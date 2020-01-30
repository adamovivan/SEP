package rs.ac.uns.ftn.scientific_center.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rs.ac.uns.ftn.scientific_center.dto.CompletePaymentDTO;
import rs.ac.uns.ftn.scientific_center.dto.PricelistItemDTO;
import rs.ac.uns.ftn.scientific_center.dto.request.PaymentOrderRequest;
import rs.ac.uns.ftn.scientific_center.dto.response.PaymentOrderResponse;
import rs.ac.uns.ftn.scientific_center.dto.response.SimpleResponse;
import rs.ac.uns.ftn.scientific_center.mapper.PricelistItemMapper;
import rs.ac.uns.ftn.scientific_center.model.*;
import rs.ac.uns.ftn.scientific_center.repository.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private PricelistItemRepository pricelistItemRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private OpenAccessRequestRepository openAccessRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PricelistItemMapper pricelistItemMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${payment.url}")
    private String paymentUrl;

    @Value("${payment.callback.url}")
    private String paymentCallbackUrl;

    public ShoppingCart addMagazine(Long magazineId) throws NullPointerException {
        PricelistItem pricelistItemMagazine = pricelistItemRepository.findByMembershipMagazineIdAndMembershipSubscriptionType(magazineId, SubscriptionType.READER_PAYS);

        if(pricelistItemMagazine == null){
            throw new NullPointerException();
        }

        ShoppingCart shoppingCart = getAuthenticatedUserShoppingCart();

        boolean needToClear = false;
        Iterator<PricelistItem> pricelistItemIterator = shoppingCart.getItems().iterator();
        while(pricelistItemIterator.hasNext()){
            PricelistItem plItem = pricelistItemIterator.next();
            if(!plItem.getId().equals(pricelistItemMagazine.getId()) && plItem.getMembership().getMagazine() != null){      // different magazine
                needToClear = true;
                break;
            }
            // If articles originates from different magazine
            else if(plItem.getMembership().getArticle() != null && !plItem.getMembership().getArticle()
                                                                                        .getMagazine()
                                                                                        .getId().equals(magazineId)){
                needToClear = true;
                break;
            }
        }

        if(needToClear){
            shoppingCart.getItems().clear();
        }

        shoppingCart.getItems().add(pricelistItemMagazine);
        return shoppingCartRepository.save(shoppingCart);
    }



    @SuppressWarnings("unchecked")
    public ShoppingCart addArticle(Long articleId) throws NullPointerException {
        List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority authority : grantedAuthorities) {
            if (authority.getAuthority().equals(RoleName.AUTHOR.toString())) {
                return addArticleAuthor(articleId);
            }
        }

        for (GrantedAuthority authority : grantedAuthorities) {
            if (authority.getAuthority().equals(RoleName.READER.toString())) {
                return addArticleReader(articleId);
            }
        }

        return null;
    }

    public ShoppingCart addArticleReader(Long articleId) throws NullPointerException {
        PricelistItem pricelistItemArticle = pricelistItemRepository.findByMembershipArticleId(articleId);

        if(pricelistItemArticle == null){
            throw new NullPointerException();
        }

        ShoppingCart shoppingCart = getAuthenticatedUserShoppingCart();

        boolean needToClear = false;
        Iterator<PricelistItem> pricelistItemIterator = shoppingCart.getItems().iterator();
        while(pricelistItemIterator.hasNext()){
            PricelistItem plItem = pricelistItemIterator.next();

            if(plItem.getMembership().getArticle() != null){
                // Check if articles originates from same magazine
                if(!pricelistItemArticle.getMembership().getArticle().getMagazine().getId().equals(plItem.getMembership().getArticle().getMagazine().getId())){
                    needToClear = true;
                    break;
                }
            } // magazine
            else if(plItem.getMembership().getMagazine() != null){
                if(!pricelistItemArticle.getMembership().getArticle().getMagazine().getId().equals(plItem.getMembership().getMagazine().getId())){
                    needToClear = true;
                    break;
                }
            }
        }

        if(needToClear){
            shoppingCart.getItems().clear();
        }

        shoppingCart.getItems().add(pricelistItemArticle);
        return shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart addArticleAuthor(Long articleId) throws NullPointerException {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new NullPointerException("Article doesn't exist."));
        PricelistItem pricelistItem = pricelistItemRepository.findByMembershipMagazineIdAndMembershipSubscriptionType(article.getMagazine().getId(), SubscriptionType.OPEN_ACCESS);

        if (pricelistItem == null) {
            throw new NullPointerException();
        }


        ShoppingCart shoppingCart = getAuthenticatedUserShoppingCart();
        shoppingCart.getItems().add(pricelistItem);

        rs.ac.uns.ftn.scientific_center.model.User user = userRepository.findByUsername(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

        OpenAccessRequest oar = new OpenAccessRequest();
        oar.setArticle(article);
        oar.setAuthor(user);
        oar.setMembership(pricelistItem.getMembership());
        oar.setActive(true);
        openAccessRequestRepository.save(oar);

        return shoppingCartRepository.save(shoppingCart);
    }

    public Set<PricelistItemDTO> getItems() {
        ShoppingCart shoppingCart = getAuthenticatedUserShoppingCart();
        return pricelistItemMapper.pricelistItemsToPricelistItemDTOs(shoppingCart.getItems());
    }

    private ShoppingCart getAuthenticatedUserShoppingCart() throws NullPointerException {
        User user = (User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        if(user == null){
            throw new NullPointerException();
        }

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserUsername(user.getUsername());

        if(shoppingCart == null){
            throw new NullPointerException();
        }

        return shoppingCart;
    }

    public PaymentOrderResponse pay() {
        ShoppingCart shoppingCart = getAuthenticatedUserShoppingCart();

        if(shoppingCart.getItems().size() == 0){
            return null;
        }

        PricelistItem pricelistItem = shoppingCart.getItems().iterator().next();
        SubscriptionType subscriptionType = pricelistItem.getMembership().getSubscriptionType();
        Double totalPrice = calculatePrice(shoppingCart.getItems(), subscriptionType);
        String email = pricelistItem.getMembership().getUser().getEmail();

        Transaction transaction = new Transaction();
        transaction.setItems(new HashSet<>(shoppingCart.getItems()));
        transaction.setCustomer(shoppingCart.getUser());
        transaction.setTransactionStatus(TransactionStatus.CREATED);
        transaction.setVendor(shoppingCart.getItems().iterator().next().getMembership().getUser());
        transaction.setAmount(totalPrice);
        transaction.setSubscriptionType(subscriptionType);
        transaction.setOrderId(UUID.randomUUID().toString());
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);

        return restTemplate.postForObject(paymentUrl, new PaymentOrderRequest(totalPrice, email, transaction.getOrderId(), paymentCallbackUrl),
                PaymentOrderResponse.class);
    }

    public SimpleResponse completePayment(CompletePaymentDTO completePaymentDTO){
        Transaction transaction = transactionRepository.findByOrderId(completePaymentDTO.getTransactionId());
        transaction.setTransactionStatus(completePaymentDTO.getTransactionStatus());
        transactionRepository.save(transaction);

        if(transaction.getTransactionStatus() == TransactionStatus.SUCCESS){
            if(transaction.getSubscriptionType() == SubscriptionType.OPEN_ACCESS){
                changeArticleMembership(transaction.getCustomer().getShoppingCart());
            }

            transaction.getCustomer().getShoppingCart().getItems().clear();
            shoppingCartRepository.save(transaction.getCustomer().getShoppingCart());
        }

        return new SimpleResponse("Success.");
    }

    private void changeArticleMembership(ShoppingCart shoppingCart){
        Iterator<PricelistItem> itemIterator = shoppingCart.getItems().iterator();
        while(itemIterator.hasNext()){
            Membership membership = itemIterator.next().getMembership();
            List<OpenAccessRequest> oars = openAccessRequestRepository.findByMembershipIdAndAuthorUsernameAndActive(membership.getId(), ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername(), true);

            for(OpenAccessRequest oar: oars){
                Membership articleMembership = membershipRepository.findByArticleId(oar.getArticle().getId());
                articleMembership.setSubscriptionType(SubscriptionType.OPEN_ACCESS);
                membershipRepository.save(articleMembership);
                oar.setActive(false);
                openAccessRequestRepository.save(oar);
            }
        }
    }

    public Boolean removeItem(Long itemId){
        ShoppingCart shoppingCart = getAuthenticatedUserShoppingCart();
        Iterator<PricelistItem> pricelistItemIterator = shoppingCart.getItems().iterator();
        while(pricelistItemIterator.hasNext()){
            PricelistItem pricelistItem = pricelistItemIterator.next();
            if(pricelistItem.getId().equals(itemId)){
                shoppingCart.getItems().remove(pricelistItem);
                shoppingCartRepository.save(shoppingCart);
                return true;
            }
        }
        return true;
    }

    private Double calculatePrice(Set<PricelistItem> items, SubscriptionType subscriptionType){
        double total = 0D;
        for(PricelistItem pricelistItem: items){
            if(pricelistItem.getMembership().getSubscriptionType() != subscriptionType){
                continue;
            }
            total += pricelistItem.getPrice();
        }
        return total;
    }
}

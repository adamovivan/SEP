package rs.ac.uns.ftn.scientific_center.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rs.ac.uns.ftn.scientific_center.dto.PricelistItemDTO;
import rs.ac.uns.ftn.scientific_center.dto.request.PaymentOrderRequest;
import rs.ac.uns.ftn.scientific_center.dto.response.PaymentOrderResponse;
import rs.ac.uns.ftn.scientific_center.mapper.PricelistItemMapper;
import rs.ac.uns.ftn.scientific_center.model.PricelistItem;
import rs.ac.uns.ftn.scientific_center.model.ShoppingCart;
import rs.ac.uns.ftn.scientific_center.model.SubscriptionType;
import rs.ac.uns.ftn.scientific_center.repository.PricelistItemRepository;
import rs.ac.uns.ftn.scientific_center.repository.ShoppingCartRepository;

import java.util.Iterator;
import java.util.Set;


@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private PricelistItemRepository pricelistItemRepository;

    @Autowired
    private PricelistItemMapper pricelistItemMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${payment.url}")
    private String paymentUrl;

    public ShoppingCart addMagazine(Long magazineId) throws NullPointerException {
        PricelistItem pricelistItemMagazine = pricelistItemRepository.findByMembershipMagazineId(magazineId);

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

    public ShoppingCart addArticle(Long articleId) throws NullPointerException {
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

        Double totalPrice = calculatePrice(shoppingCart.getItems());

        if(shoppingCart.getItems().size() == 0){
            return null;
        }

        PricelistItem pricelistItem = shoppingCart.getItems().iterator().next();
        String email = pricelistItem.getMembership().getUser().getEmail();

        return restTemplate.postForObject(paymentUrl, new PaymentOrderRequest(totalPrice, email),
                PaymentOrderResponse.class);
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

    private Double calculatePrice(Set<PricelistItem> items){
        double total = 0D;
        for(PricelistItem pricelistItem: items){
            if(pricelistItem.getMembership().getSubscriptionType() == SubscriptionType.OPEN_ACCESS){
                continue;
            }
            total += pricelistItem.getPrice();
        }
        return total;
    }
}

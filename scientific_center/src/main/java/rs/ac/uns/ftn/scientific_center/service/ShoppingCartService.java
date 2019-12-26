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
import rs.ac.uns.ftn.scientific_center.repository.MembershipRepository;
import rs.ac.uns.ftn.scientific_center.repository.PricelistItemRepository;
import rs.ac.uns.ftn.scientific_center.repository.ShoppingCartRepository;


@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private PricelistItemRepository pricelistItemRepository;

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private PricelistItemMapper pricelistItemMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${payment.url}")
    private String paymentUrl;

    public ShoppingCart addItem(Long magazineId) throws NullPointerException {
        PricelistItem pricelistItem = pricelistItemRepository.findByMagazineId(magazineId);

        if(pricelistItem == null){
            throw new NullPointerException();
        }

        ShoppingCart shoppingCart = getAuthenticatedUserShoppingCart();

        shoppingCart.setItem(pricelistItem);
        return shoppingCartRepository.save(shoppingCart);
    }

    public PricelistItemDTO getItem() {
        ShoppingCart shoppingCart = getAuthenticatedUserShoppingCart();
        return pricelistItemMapper.pricelistItemToPricelistItemDTO(shoppingCart.getItem());
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

        PricelistItem shoppingCartItem = shoppingCart.getItem();
        Double totalPrice = shoppingCartItem.getPrice();
        String email = membershipRepository.findByMagazineId(shoppingCartItem
                    .getMagazine()
                    .getId())
                            .getUser()
                            .getEmail();
        return restTemplate.postForObject(paymentUrl, new PaymentOrderRequest(totalPrice, email),
                PaymentOrderResponse.class);
    }
}

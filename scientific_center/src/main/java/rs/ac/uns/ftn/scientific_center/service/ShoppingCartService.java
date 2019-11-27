package rs.ac.uns.ftn.scientific_center.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
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
import rs.ac.uns.ftn.scientific_center.repository.PricelistItemRepository;
import rs.ac.uns.ftn.scientific_center.repository.ShoppingCartRepository;

import java.util.List;
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

    public ShoppingCart addItem(Long magazineId) throws NullPointerException {
        PricelistItem pricelistItem = pricelistItemRepository.findByMagazineId(magazineId);

        if(pricelistItem == null){
            throw new NullPointerException();
        }

        ShoppingCart shoppingCart = getAuthenticatedUserShoppingCart();

        shoppingCart.getItems().add(pricelistItem);
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

    public PaymentOrderResponse pay(PaymentOrderRequest paymentOrderRequest){
        return restTemplate.postForObject("http://localhost:8002/pay", paymentOrderRequest, PaymentOrderResponse.class);
    }
}

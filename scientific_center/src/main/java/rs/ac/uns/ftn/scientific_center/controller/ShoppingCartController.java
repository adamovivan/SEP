package rs.ac.uns.ftn.scientific_center.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.scientific_center.dto.PricelistItemDTO;
import rs.ac.uns.ftn.scientific_center.dto.response.PaymentOrderResponse;
import rs.ac.uns.ftn.scientific_center.dto.response.SimpleResponse;
import rs.ac.uns.ftn.scientific_center.service.ShoppingCartService;

import java.util.Set;


@RequestMapping(value = "/shopping-cart")
@RestController
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @RequestMapping(value = "/add-magazine/{magazineId}", method = RequestMethod.POST)
    public ResponseEntity<SimpleResponse> addMagazine(@PathVariable Long magazineId){
        shoppingCartService.addMagazine(magazineId);
        return ResponseEntity.ok(new SimpleResponse("Item successfully added."));
    }

    @RequestMapping(value = "/add-article/{articleId}", method = RequestMethod.POST)
    public ResponseEntity<SimpleResponse> addArticle(@PathVariable Long articleId){
        shoppingCartService.addArticle(articleId);
        return ResponseEntity.ok(new SimpleResponse("Item successfully added."));
    }

    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public ResponseEntity<Set<PricelistItemDTO>> getItems(){
        return ResponseEntity.ok(shoppingCartService.getItems());
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public ResponseEntity<PaymentOrderResponse> pay(){
        return ResponseEntity.ok().body(shoppingCartService.pay());
    }

    @RequestMapping(value = "/remove/{itemId}", method = RequestMethod.DELETE)
    public ResponseEntity removeItem(@PathVariable Long itemId){
        return ResponseEntity.ok().body(shoppingCartService.removeItem(itemId));
    }


}

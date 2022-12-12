package org.msvdev.ee.shop.cart.controller;

import lombok.RequiredArgsConstructor;
import org.msvdev.ee.shop.api.cart.CartDto;
import org.msvdev.ee.shop.cart.mapper.CartMapper;
import org.msvdev.ee.shop.cart.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
@CrossOrigin(origins = "http://localhost:8080")
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;


    @GetMapping
    public CartDto getCurrentCart(Principal principal) {
        return cartMapper.modelToDto(cartService.getCurrentCart(principal.getName()));
    }


    /**
     * Добавить единицу товара в корзину
     * @param id идентификатор добавляемого товара
     */
    @PutMapping("/add/{id}")
    public void addToCart(Principal principal, @PathVariable Long id) {
        cartService.add(principal.getName(), id);
    }


    /**
     * Удалить единицу товара из корзины
     * @param id идентификатор удаляемого товара
     */
    @PutMapping("/sub/{id}")
    public void subFromCart(Principal principal, @PathVariable Long id) {
        cartService.sub(principal.getName(), id);
    }


    /**
     * Удалить товар полностью из корзины
     * @param id идентификатор удаляемого товара
     */
    @PutMapping("/remove/{id}")
    public void removeFromCart(Principal principal, @PathVariable Long id) {
        cartService.remove(principal.getName(), id);
    }


    /**
     * Очистить корзину
     */
    @DeleteMapping
    public void clearCart(Principal principal) {
        cartService.clear(principal.getName());
    }

}

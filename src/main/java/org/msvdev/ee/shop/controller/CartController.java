package org.msvdev.ee.shop.controller;

import lombok.RequiredArgsConstructor;
import org.msvdev.ee.shop.dto.Cart;
import org.msvdev.ee.shop.service.CartService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;


    @GetMapping
    public Cart getCurrentCart() {
        return cartService.getCurrentCart();
    }


    /**
     * Добавить единицу товара в корзину
     * @param id идентификатор добавляемого товара
     */
    @PutMapping("/add/{id}")
    public void addToCart(@PathVariable Long id) {
        cartService.add(id);
    }


    /**
     * Удалить единицу товара из корзины
     * @param id идентификатор удаляемого товара
     */
    @PutMapping("/sub/{id}")
    public void subFromCart(@PathVariable Long id) {
        cartService.sub(id);
    }


    /**
     * Удалить товар полностью из корзины
     * @param id идентификатор удаляемого товара
     */
    @PutMapping("/remove/{id}")
    public void removeFromCart(@PathVariable Long id) {
        cartService.remove(id);
    }


    /**
     * Очистить корзину
     */
    @DeleteMapping
    public void clearCart() {
        cartService.clear();
    }

}

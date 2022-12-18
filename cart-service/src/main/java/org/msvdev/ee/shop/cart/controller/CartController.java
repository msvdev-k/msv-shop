package org.msvdev.ee.shop.cart.controller;

import lombok.RequiredArgsConstructor;
import org.msvdev.ee.shop.api.cart.CartDto;
import org.msvdev.ee.shop.cart.mapper.CartMapper;
import org.msvdev.ee.shop.cart.service.CartService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;


    @GetMapping
    public CartDto getCurrentCart(@RequestHeader String username, @RequestHeader String[] roles) {
        return cartMapper.modelToDto(cartService.getCurrentCart(username));
    }


    /**
     * Добавить единицу товара в корзину
     * @param id идентификатор добавляемого товара
     */
    @PutMapping("/add/{id}")
    public void addToCart(@RequestHeader String username, @PathVariable Long id) {
        cartService.add(username, id);
    }


    /**
     * Удалить единицу товара из корзины
     * @param id идентификатор удаляемого товара
     */
    @PutMapping("/sub/{id}")
    public void subFromCart(@RequestHeader String username, @PathVariable Long id) {
        cartService.sub(username, id);
    }


    /**
     * Удалить товар полностью из корзины
     * @param id идентификатор удаляемого товара
     */
    @PutMapping("/remove/{id}")
    public void removeFromCart(@RequestHeader String username, @PathVariable Long id) {
        cartService.remove(username, id);
    }


    /**
     * Очистить корзину
     */
    @DeleteMapping
    public void clearCart(@RequestHeader String username) {
        cartService.clear(username);
    }

}

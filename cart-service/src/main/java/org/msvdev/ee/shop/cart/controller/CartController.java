package org.msvdev.ee.shop.cart.controller;

import lombok.RequiredArgsConstructor;
import org.msvdev.ee.shop.api.cart.CartDto;
import org.msvdev.ee.shop.cart.mapper.CartMapper;
import org.msvdev.ee.shop.cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;


    /**
     * Получить идентификатор гостевой корзины
     */
    @GetMapping("/guest_cart_id")
    public ResponseEntity<String> getGuestCartId() {
        return ResponseEntity.ok(UUID.randomUUID().toString());
    }


    /**
     * Получить текущую корзину
     */
    @GetMapping("/{guestCartId}")
    public CartDto getCurrentCart(@RequestHeader(required = false) String username,
                                  @PathVariable String guestCartId) {
        String currentCartID = guestCartId;

        if (username != null) {
            cartService.mergeCarts(username, guestCartId);
            currentCartID = username;
        }

        return cartMapper.modelToDto(cartService.getCurrentCart(currentCartID));
    }


    /**
     * Добавить единицу товара в корзину
     * @param id идентификатор добавляемого товара
     */
    @PutMapping("/{guestCartId}/add/{id}")
    public void addToCart(@RequestHeader(required = false) String username,
                          @PathVariable String guestCartId,
                          @PathVariable Long id) {
        String currentCartID = username != null ? username : guestCartId;
        cartService.add(currentCartID, id);
    }


    /**
     * Удалить единицу товара из корзины
     * @param id идентификатор удаляемого товара
     */
    @PutMapping("/{guestCartId}/sub/{id}")
    public void subFromCart(@RequestHeader(required = false) String username,
                            @PathVariable String guestCartId,
                            @PathVariable Long id) {
        String currentCartID = username != null ? username : guestCartId;
        cartService.sub(currentCartID, id);
    }


    /**
     * Удалить товар полностью из корзины
     * @param id идентификатор удаляемого товара
     */
    @PutMapping("/{guestCartId}/remove/{id}")
    public void removeFromCart(@RequestHeader(required = false) String username,
                               @PathVariable String guestCartId,
                               @PathVariable Long id) {
        String currentCartID = username != null ? username : guestCartId;
        cartService.remove(currentCartID, id);
    }


    /**
     * Очистить корзину
     */
    @DeleteMapping("/{guestCartId}")
    public void clearCart(@RequestHeader(required = false) String username,
                          @PathVariable String guestCartId) {
        String currentCartID = username != null ? username : guestCartId;
        cartService.clear(currentCartID);
    }

}

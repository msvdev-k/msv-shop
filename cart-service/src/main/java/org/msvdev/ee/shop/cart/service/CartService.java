package org.msvdev.ee.shop.cart.service;

import lombok.RequiredArgsConstructor;
import org.msvdev.ee.shop.api.ProductDto;
import org.msvdev.ee.shop.api.exception.ResourceNotFoundException;
import org.msvdev.ee.shop.cart.integration.ProxyProductServiceIntegration;
import org.msvdev.ee.shop.cart.model.Cart;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CartService {

    private final ProxyProductServiceIntegration productServiceIntegration;
    private final RedisTemplate<String, Cart> redisTemplate;


    /**
     * Получить текущую корзину пользователя
     * @param username имя пользователя
     */
    public Cart getCurrentCart(String username) {

        if (Boolean.FALSE.equals(redisTemplate.hasKey(username))) {
            redisTemplate.opsForValue().set(username, new Cart());
        }

        return redisTemplate.opsForValue().get(username);
    }


    /**
     * Добавить единицу товара в корзину
     * @param username имя пользователя
     * @param productId идентификатор добавляемого товара
     */
    public void add(String username, Long productId) {
        ProductDto product = productServiceIntegration.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Error when adding product with id: " +
                                productId.toString() +
                                " to cart. Product not found")
                );

        Cart cart = getCurrentCart(username);
        cart.add(product);
        redisTemplate.opsForValue().set(username, cart);
    }


    /**
     * Вычесть единицу товара из корзины
     * @param username имя пользователя
     * @param productId идентификатор убираемого товара
     */
    public void sub(String username, Long productId) {
        ProductDto product = productServiceIntegration.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Error when subtraction product with id: " +
                                productId.toString() +
                                " from cart. Product not found")
                );

        Cart cart = getCurrentCart(username);
        cart.sub(product);
        redisTemplate.opsForValue().set(username, cart);
    }


    /**
     * Удалить товар из корзины пользователя
     * @param username имя пользователя
     * @param productId идентификатор удаляемого товара
     */
    public void remove(String username, Long productId) {
        Cart cart = getCurrentCart(username);
        cart.remove(productId);
        redisTemplate.opsForValue().set(username, cart);
    }


    /**
     * Очистить корзину пользователя
     * @param username имя пользователя
     */
    public void clear(String username) {
        redisTemplate.delete(username);
    }


    /**
     * Объединение двух корзин с товарами
     * @param username имя пользователя (общая корзина)
     * @param guestCartId идентификатор гостевой корзины
     */
    public void mergeCarts(String username, String guestCartId) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(guestCartId))) {

            Cart guestCart = redisTemplate.opsForValue().get(guestCartId);
            redisTemplate.delete(guestCartId);
            if (guestCart == null) return;

            Cart cart = getCurrentCart(username);
            cart.merge(guestCart);

            redisTemplate.opsForValue().set(username, cart);
        }
    }
}

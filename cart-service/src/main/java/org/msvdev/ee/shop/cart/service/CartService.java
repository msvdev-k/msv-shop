package org.msvdev.ee.shop.cart.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.msvdev.ee.shop.api.ProductDto;
import org.msvdev.ee.shop.api.exception.ResourceNotFoundException;
import org.msvdev.ee.shop.cart.model.Cart;
import org.msvdev.ee.shop.cart.integration.ProductServiceIntegration;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductServiceIntegration productServiceIntegration;
    private Map<String, Cart> mapCart;


    @PostConstruct
    public void init() {
        mapCart = new HashMap<>();
    }


    /**
     * Получить текущую корзину пользователя
     * @param username имя пользователя
     */
    public Cart getCurrentCart(String username) {

        if (!mapCart.containsKey(username)) {
            mapCart.put(username, new Cart());
        }

        return mapCart.get(username);
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
    }


    /**
     * Удалить товар из корзины пользователя
     * @param username имя пользователя
     * @param productId идентификатор удаляемого товара
     */
    public void remove(String username, Long productId) {
        Cart cart = getCurrentCart(username);
        cart.remove(productId);
    }


    /**
     * Очистить корзину пользователя
     * @param username имя пользователя
     */
    public void clear(String username) {
        mapCart.remove(username);
    }

}

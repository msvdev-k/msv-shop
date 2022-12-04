package org.msvdev.ee.shop.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.msvdev.ee.shop.dto.Cart;
import org.msvdev.ee.shop.entity.Product;
import org.msvdev.ee.shop.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductService productService;
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
        Product product = productService.findById(productId)
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
        Product product = productService.findById(productId)
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

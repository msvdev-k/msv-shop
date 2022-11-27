package org.msvdev.ee.shop.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.msvdev.ee.shop.dto.Cart;
import org.msvdev.ee.shop.entity.Product;
import org.msvdev.ee.shop.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductService productService;
    private Cart demoCart;


    @PostConstruct
    public void init() {
        demoCart = new Cart();
    }


    /**
     * Получить текущую корзину
     */
    public Cart getCurrentCart() {
        return demoCart;
    }


    /**
     * Добавить единицу товара в корзину
     * @param productId идентификатор добавляемого товара
     */
    public void add(Long productId) {
        Product product = productService.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Error when adding product with id: " +
                                productId.toString() +
                                " to cart. Product not found")
                );
        demoCart.add(product);
    }


    /**
     * Вычесть единицу товара из корзины
     * @param productId идентификатор убираемого товара
     */
    public void sub(Long productId) {
        Product product = productService.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Error when subtraction product with id: " +
                                productId.toString() +
                                " from cart. Product not found")
                );
        demoCart.sub(product);
    }


    /**
     * Удалить товар из корзины
     * @param productId идентификатор удаляемого товара
     */
    public void remove(Long productId) {
        demoCart.remove(productId);
    }


    /**
     * Очистить корзину
     */
    public void clear() {
        demoCart.clear();
    }

}

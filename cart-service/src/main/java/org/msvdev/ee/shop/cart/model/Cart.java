package org.msvdev.ee.shop.cart.model;

import org.msvdev.ee.shop.api.ProductDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Cart {

    private final List<CartItem> items;
    private BigDecimal totalPrice;


    public Cart() {
        items = new ArrayList<>();
        totalPrice = BigDecimal.ZERO;
    }


    /**
     * Получить список позиций, сохранённых в корзине
     */
    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }


    /**
     * Сохранить список позиций в корзине
     */
    public void setItems(List<CartItem> items) {
        this.items.clear();
        this.items.addAll(items);
        calculateTotalPrice();
    }


    public BigDecimal getTotalPrice() {
        return totalPrice;
    }


    public void setTotalPrice(BigDecimal totalPrice) {
    }


    /**
     * Добавить единицу товара в корзину
     * @param product добавляемый товар
     */
    public void add(ProductDto product) {
        for (CartItem item: items) {
            if (item.getProductId().equals(product.getId())) {
                item.changeQuantity(1);
                calculateTotalPrice();
                return;
            }
        }

        items.add(new CartItem(product.getId(), product.getTitle(), product.getPrice()));
        calculateTotalPrice();
    }

    /**
     * Удалить единицу товара из корзины
     * @param product убираемый товар
     */
    public void sub(ProductDto product) {
        for (CartItem item: items) {
            if (item.getProductId().equals(product.getId())) {
                item.changeQuantity(-1);
                calculateTotalPrice();
                return;
            }
        }
    }


    /**
     * Удалить товар из корзины
     * @param productId идентификатор удаляемого товара
     */
    public void remove(Long productId) {
        if (items.removeIf(item -> item.getProductId().equals(productId))) {
            calculateTotalPrice();
        }
    }


    /**
     * Очистить корзину
     */
    public void clear() {
        items.clear();
        totalPrice = BigDecimal.ZERO;
    }


    /**
     * Рассчитать полную стоимость товаров в корзине
     */
    private void calculateTotalPrice() {
        totalPrice = BigDecimal.ZERO;
        for (CartItem item: items) {
            totalPrice = totalPrice.add(item.cartItemPrice());
        }
    }


    /**
     * Объединение текущей корзины с гостевой
     * @param guestCart гостевая корзина
     */
    public void merge(Cart guestCart) {
        guestCart.getItems().forEach(guestCartItem ->{

            boolean flag = true;

            for (CartItem item: items) {
                if (item.getProductId().equals(guestCartItem.getProductId())) {
                    item.changeQuantity(guestCartItem.getQuantity());
                    flag = false;
                    break;
                }
            }

            if (flag) {
                items.add(guestCartItem);
            }
        });

        calculateTotalPrice();
    }
}

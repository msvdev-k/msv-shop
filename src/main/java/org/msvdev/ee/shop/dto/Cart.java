package org.msvdev.ee.shop.dto;

import lombok.Getter;
import org.msvdev.ee.shop.entity.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Getter
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
     * Добавить единицу товара в корзину
     * @param product добавляемый товар
     */
    public void add(Product product) {
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
    public void sub(Product product) {
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
            totalPrice = totalPrice.add(item.getPrice());
        }
    }

}

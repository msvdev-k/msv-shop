package org.msvdev.ee.shop.cart.model;

import lombok.Getter;

import java.math.BigDecimal;


@Getter
public class CartItem {

    private final Long productId;
    private final String productTitle;
    private int quantity;
    private final BigDecimal unitPrice;
    private BigDecimal price;


    public CartItem(Long productId, String productTitle, BigDecimal unitPrice) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.quantity = 1;
        this.unitPrice = unitPrice;
        calculatePrice();
    }


    /**
     * Изменить количество товара в одной позиции
     * @param delta величина на которую производится изменение
     */
    public void changeQuantity(int delta) {
        quantity += delta;
        if (quantity < 0) {
            quantity = 0;
        }
        calculatePrice();
    }


    /**
     * Подсчитать полную стоимость товара в одной позиции
     */
    private void calculatePrice() {
        price = unitPrice.multiply(new BigDecimal(quantity));
    }
}

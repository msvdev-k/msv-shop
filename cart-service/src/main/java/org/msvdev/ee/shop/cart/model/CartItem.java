package org.msvdev.ee.shop.cart.model;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class CartItem {

    private Long productId;
    private String productTitle;
    private int quantity;
    private BigDecimal unitPrice;


    public CartItem() {
    }


    public CartItem(Long productId, String productTitle, BigDecimal unitPrice) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.quantity = 1;
        this.unitPrice = unitPrice;
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
    }


    /**
     * Подсчитать полную стоимость товара в одной позиции
     */
    public BigDecimal cartItemPrice() {
        return unitPrice.multiply(new BigDecimal(quantity));
    }
}

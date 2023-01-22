package org.msvdev.ee.shop.api.cart;

import java.math.BigDecimal;


public class CartItemDto {

    private final Long productId;
    private final String productTitle;
    private final Integer quantity;
    private final BigDecimal unitPrice;
    private final BigDecimal price;


    private CartItemDto(Long productId, String productTitle, int quantity, BigDecimal unitPrice, BigDecimal price) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.price = price;
    }


    public Long getProductId() {
        return productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }


    public static CartItemDtoBuilder builder() {
        return new CartItemDtoBuilder();
    }

    public static class CartItemDtoBuilder {
        private Long productId;
        private String productTitle;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal price;

        CartItemDtoBuilder() {
        }

        public CartItemDtoBuilder productId(Long productId) {
            this.productId = productId;
            return this;
        }

        public CartItemDtoBuilder productTitle(String productTitle) {
            this.productTitle = productTitle;
            return this;
        }

        public CartItemDtoBuilder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public CartItemDtoBuilder unitPrice(BigDecimal unitPrice) {
            this.unitPrice = unitPrice;
            return this;
        }

        public CartItemDtoBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public CartItemDto build() {
            return new CartItemDto(productId, productTitle, quantity, unitPrice, price);
        }
    }
}

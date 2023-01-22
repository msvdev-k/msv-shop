package org.msvdev.ee.shop.cart.mapper;

import org.msvdev.ee.shop.api.cart.CartDto;
import org.msvdev.ee.shop.api.cart.CartItemDto;
import org.msvdev.ee.shop.cart.model.Cart;
import org.msvdev.ee.shop.cart.model.CartItem;
import org.springframework.stereotype.Component;


@Component
public class CartMapper {

    public CartItemDto modelToDto(CartItem cartItem) {

        return CartItemDto
                .builder()
                .productId(cartItem.getProductId())
                .productTitle(cartItem.getProductTitle())
                .quantity(cartItem.getQuantity())
                .unitPrice(cartItem.getUnitPrice())
                .price(cartItem.cartItemPrice())
                .build();
    }


    public CartDto modelToDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setTotalPrice(cart.getTotalPrice());
        cartDto.setItems(cart.getItems().stream().map(this::modelToDto).toList());
        return cartDto;
    }


}
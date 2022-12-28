package org.msvdev.ee.shop.cart;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.msvdev.ee.shop.api.ProductDto;
import org.msvdev.ee.shop.api.exception.ResourceNotFoundException;
import org.msvdev.ee.shop.cart.integration.ProductServiceIntegration;
import org.msvdev.ee.shop.cart.model.Cart;
import org.msvdev.ee.shop.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;


@SpringBootTest(classes = CartService.class)
public class CartServiceTests {

    @Autowired
    private CartService cartService;

    @MockBean
    private ProductServiceIntegration productServiceIntegration;

    private String username;


    @BeforeEach
    public void init() {
        username = "user";

        ProductDto[] products = new ProductDto[]{
                new ProductDto(0L, "Картофель", null, new BigDecimal(55)),
                new ProductDto(1L, "Помидоры", null, new BigDecimal(180)),
                new ProductDto(2L, "Морковь", null, new BigDecimal(60)),
                new ProductDto(3L, "Огурцы", null, new BigDecimal(150)),
                new ProductDto(4L, "Свёкла", null, new BigDecimal(90)),
                new ProductDto(5L, "Редис", null, new BigDecimal(240)),
                new ProductDto(6L, "Капуста", null, new BigDecimal(75)),
                new ProductDto(7L, "Лук", null, new BigDecimal(65)),
                new ProductDto(8L, "Чеснок", null, new BigDecimal(128)),
                new ProductDto(9L, "Яблоки", null, new BigDecimal(260)),
                new ProductDto(10L, "Груши", null, new BigDecimal(270)),
                new ProductDto(11L, "Бананы", null, new BigDecimal(148)),
                new ProductDto(12L, "Виноград", null, new BigDecimal(340)),
                new ProductDto(13L, "Мандарины", null, new BigDecimal(320)),
                new ProductDto(14L, "Апельсины", null, new BigDecimal(300)),
                new ProductDto(15L, "Киви", null, new BigDecimal(360)),
                new ProductDto(16L, "Персики", null, new BigDecimal(280)),
                new ProductDto(17L, "Слива", null, new BigDecimal(250)),
                new ProductDto(18L, "Помело", null, new BigDecimal(390)),
                new ProductDto(19L, "Изюм", null, new BigDecimal(210))
        };

        for (ProductDto product : products) {
            Mockito.doReturn(Optional.of(product))
                    .when(productServiceIntegration)
                    .findById(product.getId());
        }
    }


    @Test
    public void addSubRemoveProductTest() {
        cartService.clear(username);
        Cart cart = cartService.getCurrentCart(username);

        Assertions.assertEquals(cart.getItems().size(), 0);
        Assertions.assertEquals(cart.getTotalPrice(), BigDecimal.ZERO);

        cartService.add(username, 0L);
        cartService.add(username, 1L);
        cartService.add(username, 1L);
        cartService.add(username, 2L);
        cartService.add(username, 5L);
        cartService.add(username, 5L);
        cartService.add(username, 5L);

        Mockito.verify(productServiceIntegration, Mockito.times(7))
                .findById(ArgumentMatchers.any());

        Assertions.assertEquals(cart.getItems().size(), 4);
        Assertions.assertEquals(cart.getTotalPrice(), new BigDecimal(55+2*180+60+3*240));

        cartService.sub(username, 2L);
        cartService.sub(username, 5L);

        Mockito.verify(productServiceIntegration, Mockito.times(9))
                .findById(ArgumentMatchers.any());

        Assertions.assertEquals(cart.getItems().size(), 4);
        Assertions.assertEquals(cart.getTotalPrice(), new BigDecimal(55+2*180+2*240));

        cartService.remove(username, 2L);
        cartService.remove(username, 20L);

        Mockito.verify(productServiceIntegration, Mockito.times(9))
                .findById(ArgumentMatchers.any());

        Assertions.assertEquals(cart.getItems().size(), 3);
        Assertions.assertEquals(cart.getTotalPrice(), new BigDecimal(55+2*180+2*240));

        cartService.remove(username, 5L);

        Mockito.verify(productServiceIntegration, Mockito.times(9))
                .findById(ArgumentMatchers.any());

        Assertions.assertEquals(cart.getItems().size(), 2);
        Assertions.assertEquals(cart.getTotalPrice(), new BigDecimal(55+2*180));
    }


    @Test
    public void productNotFoundExceptionTest() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            cartService.add(username, 30L);
        });

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            cartService.sub(username, 55L);
        });
    }
}
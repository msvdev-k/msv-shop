package org.msvdev.ee.shop.cart;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.msvdev.ee.shop.api.ProductDto;
import org.msvdev.ee.shop.cart.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;


@SpringBootTest
public class RedisTemplateTests {

    @Autowired
    private RedisTemplate<String, Cart> redisTemplate;


    private String username;
    private ProductDto[] products;


    @BeforeEach
    public void init() {
        username = "user";

        products = new ProductDto[]{
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

    }


    @Test
    public void addSubRemoveClearProductTest() {
        redisTemplate.opsForValue().set(username, new Cart());

        Cart cart = redisTemplate.opsForValue().get(username);

        Assertions.assertNotNull(cart);
        Assertions.assertEquals(cart.getItems().size(), 0);
        Assertions.assertEquals(cart.getTotalPrice(), BigDecimal.ZERO);

        /////////////////////////////////////////////////////////////////
        cart.add(products[0]);
        cart.add(products[1]);
        cart.add(products[1]);
        cart.add(products[2]);
        cart.add(products[5]);
        cart.add(products[5]);
        cart.add(products[5]);

        redisTemplate.opsForValue().set(username, cart);


        cart = redisTemplate.opsForValue().get(username);

        Assertions.assertNotNull(cart);
        Assertions.assertEquals(cart.getItems().size(), 4);
        Assertions.assertEquals(cart.getTotalPrice(), new BigDecimal(55+2*180+60+3*240));


        /////////////////////////////////////////////////////////////////
        cart.sub(products[2]);
        cart.sub(products[5]);

        redisTemplate.opsForValue().set(username, cart);


        cart = redisTemplate.opsForValue().get(username);

        Assertions.assertNotNull(cart);
        Assertions.assertEquals(cart.getItems().size(), 4);
        Assertions.assertEquals(cart.getTotalPrice(), new BigDecimal(55+2*180+2*240));


        /////////////////////////////////////////////////////////////////
        cart.remove(2L);
        cart.remove(20L);

        redisTemplate.opsForValue().set(username, cart);


        cart = redisTemplate.opsForValue().get(username);

        Assertions.assertNotNull(cart);
        Assertions.assertEquals(cart.getItems().size(), 3);
        Assertions.assertEquals(cart.getTotalPrice(), new BigDecimal(55+2*180+2*240));


        /////////////////////////////////////////////////////////////////
        cart.remove(5L);

        redisTemplate.opsForValue().set(username, cart);


        cart = redisTemplate.opsForValue().get(username);

        Assertions.assertNotNull(cart);
        Assertions.assertEquals(cart.getItems().size(), 2);
        Assertions.assertEquals(cart.getTotalPrice(), new BigDecimal(55+2*180));


        /////////////////////////////////////////////////////////////////
        cart.clear();

        redisTemplate.opsForValue().set(username, cart);


        cart = redisTemplate.opsForValue().get(username);

        Assertions.assertNotNull(cart);
        Assertions.assertEquals(cart.getItems().size(), 0);
        Assertions.assertEquals(cart.getTotalPrice(), BigDecimal.ZERO);


        /////////////////////////////////////////////////////////////////
        redisTemplate.delete(username);

        cart = redisTemplate.opsForValue().get(username);
        Assertions.assertNull(cart);
    }
}
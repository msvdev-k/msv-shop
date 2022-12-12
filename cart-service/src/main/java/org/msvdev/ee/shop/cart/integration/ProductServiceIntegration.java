package org.msvdev.ee.shop.cart.integration;

import org.msvdev.ee.shop.api.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@Component
public class ProductServiceIntegration {

    private final RestTemplate restTemplate;
    private final String productsRestUri;


    @Autowired
    public ProductServiceIntegration(RestTemplate restTemplate, @Value("${rest-template.products}") String productsRestUri) {
        this.restTemplate = restTemplate;
        this.productsRestUri = productsRestUri;
    }

    public Optional<ProductDto> findById(Long id) {
        return Optional.ofNullable(
                restTemplate.getForObject(
                        String.format("%s/%d", productsRestUri, id),
                        ProductDto.class));
    }

}
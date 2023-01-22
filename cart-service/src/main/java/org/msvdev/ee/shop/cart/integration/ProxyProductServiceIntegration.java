package org.msvdev.ee.shop.cart.integration;

import lombok.RequiredArgsConstructor;
import org.msvdev.ee.shop.api.ProductDto;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


@Component
@RequiredArgsConstructor
public class ProxyProductServiceIntegration {

    private final ProductServiceIntegration productServiceIntegration;
    private final Map<Long, Optional<ProductDto>> products = new ConcurrentHashMap<>();


    public Optional<ProductDto> findById(Long id) {

        if (products.containsKey(id)) {
            return products.get(id);
        }

        Optional<ProductDto> productDto = productServiceIntegration.findById(id);
        products.put(id, productDto);

        return productDto;
    }

}
package org.msvdev.ee.shop.cart.integration;

import lombok.RequiredArgsConstructor;
import org.msvdev.ee.shop.api.ProductDto;
import org.msvdev.ee.shop.api.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {

    private final WebClient productServiceWebClient;


    public Optional<ProductDto> findById(Long id) {

        ProductDto productDto = productServiceWebClient.get()
                .uri(String.format("%s/%d", "/api/v1/products", id))
                .retrieve()
                .onStatus(
                        httpStatusCode -> httpStatusCode.value() == HttpStatus.NOT_FOUND.value(),
                        clientResponse -> Mono.error(new ResourceNotFoundException("Товар не найден"))
                )
                .bodyToMono(ProductDto.class)
                .block();

        return Optional.ofNullable(productDto);
    }

}
package org.msvdev.ee.shop.mapper;

import org.msvdev.ee.shop.api.ProductDto;
import org.msvdev.ee.shop.entity.Product;
import org.springframework.stereotype.Component;


@Component
public class ProductMapper {

    public Product dtoToEntity(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getTitle(), productDto.getDescription(), productDto.getPrice());
    }

    public ProductDto entityToDto(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getDescription(), product.getPrice());
    }

}
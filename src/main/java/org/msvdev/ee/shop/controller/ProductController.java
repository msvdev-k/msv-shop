package org.msvdev.ee.shop.controller;

import lombok.RequiredArgsConstructor;
import org.msvdev.ee.shop.api.ProductDto;
import org.msvdev.ee.shop.api.exception.BadRequestException;
import org.msvdev.ee.shop.api.exception.ResourceNotFoundException;
import org.msvdev.ee.shop.entity.Product;
import org.msvdev.ee.shop.mapper.ProductMapper;
import org.msvdev.ee.shop.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "http://localhost:8080")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;


    @GetMapping
    public Page<ProductDto> getAllProducts(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "page_size", defaultValue = "10") Integer pageSize) {

        if (page < 1) {
            throw new BadRequestException("The page parameter must be greater than zero");
        }
        if (pageSize < 2) {
            throw new BadRequestException("The page_size parameter must be greater than 1");
        }

        Page<Product> products = productService.findAll(page, pageSize);
        return products.map(productMapper::entityToDto);
    }


    @GetMapping("/{id}")
    public ProductDto findProductById(@PathVariable(value = "id") Long id) {
        Product product = productService.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product with id = " + id + " not found")
        );

        return productMapper.entityToDto(product);
    }

}
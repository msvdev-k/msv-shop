package org.msvdev.ee.shop.endpoint;

import lombok.RequiredArgsConstructor;
import org.msvdev.ee.shop.service.ProductService;
import org.msvdev.ee.shop.soap.product.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;
import java.util.Optional;


@Endpoint
@RequiredArgsConstructor
public class ProductEndpoint {

    private static final String NAMESPACE_URI = "http://msvdev.org/ee/shop/ws/products";

    private final ProductService productService;


    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) {
        List<Product> soapProducts =
                productService.findAll().stream().map(
                        product -> {
                            Product soapProduct = new Product();
                            soapProduct.setId(product.getId());
                            soapProduct.setTitle(product.getTitle());
                            soapProduct.setDescription(product.getDescription());
                            soapProduct.setPrice(product.getPrice());
                            return soapProduct;
                        }
                ).toList();

        GetAllProductsResponse response = new GetAllProductsResponse();
        response.getProducts().addAll(soapProducts);
        return response;
    }


    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIdRequest")
    public GetProductByIdResponse getProductById(@RequestPayload GetProductByIdRequest request) {

        GetProductByIdResponse response = new GetProductByIdResponse();

        Optional<org.msvdev.ee.shop.entity.Product> product = productService.findById(request.getProductId());
        if (product.isPresent()) {

            Product soapProduct = new Product();
            soapProduct.setId(product.get().getId());
            soapProduct.setTitle(product.get().getTitle());
            soapProduct.setDescription(product.get().getDescription());
            soapProduct.setPrice(product.get().getPrice());
            response.setProduct(soapProduct);
        }

        return response;
    }

}
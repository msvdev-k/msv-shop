package org.msvdev.ee.shop.cart.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "integrations.product-service")
public class ProductServiceIntegrationProperties {

    private String url;
    private Integer connectTimeout;
    private Integer readTimeout;
    private Integer writeTimeout;

}

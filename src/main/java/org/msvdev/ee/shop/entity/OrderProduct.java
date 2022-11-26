package org.msvdev.ee.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Entity
@Table(name = "order_products")
@Data
@NoArgsConstructor
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    @NotNull
    @Min(value = 1)
    @Column(name = "quantity")
    private Integer quantity;


    @NotNull
    @PositiveOrZero
    @Column(name = "price")
    private BigDecimal price;


    @NotNull
    @PositiveOrZero
    @Column(name = "total_price")
    private BigDecimal total_price;

}

package org.msvdev.ee.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collection;


@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @NotBlank
    @Column(name = "username")
    private String username;


    @NotBlank
    @Column(name = "delivery_address")
    private String deliveryAddress;


    @NotNull
    @Column(name = "delivery_date")
    private LocalDate deliveryDate;


    @NotBlank
    @Column(name = "status", length = 16)
    private String status;


    @OneToMany(mappedBy = "order")
    private Collection<OrderProduct> orderProducts;

}

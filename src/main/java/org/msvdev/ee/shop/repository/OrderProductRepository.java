package org.msvdev.ee.shop.repository;

import org.msvdev.ee.shop.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}

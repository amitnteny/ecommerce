package com.amitnteny.ecommerce.repository;

import com.amitnteny.ecommerce.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByOrderId(Long orderId);

    OrderEntity save(OrderEntity orderEntity);

    void deleteByOrderId(Long orderId);

    List<OrderEntity> findAll();
}

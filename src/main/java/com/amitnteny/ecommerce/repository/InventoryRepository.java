package com.amitnteny.ecommerce.repository;

import com.amitnteny.ecommerce.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {
    Optional<InventoryEntity> findByProductId(Long productId);

    Long countByProductId(Long productId);

    @Override
    List<InventoryEntity> findAll();

    @Override
    InventoryEntity save(InventoryEntity s);

    @Modifying
    @Query(value = "UPDATE INVENTORY i SET i.AVAILABLE_QUANTITY = :num WHERE i.PRODUCT_ID = :productId", nativeQuery = true)
    int updateInventory(@Param("productId") long productId, @Param("num") long num);
}

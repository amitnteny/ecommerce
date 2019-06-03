package com.amitnteny.ecommerce.service;

import com.amitnteny.ecommerce.domain.Inventory;
import com.amitnteny.ecommerce.entity.InventoryEntity;
import com.amitnteny.ecommerce.enums.Response;
import com.amitnteny.ecommerce.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    private InventoryRepository inventoryRepository;

    @Override
    public List<Inventory> getAllProducts() {
        return getAllProductsFromInventoryEntity(inventoryRepository.findAll());
    }

    private List<Inventory> getAllProductsFromInventoryEntity(List<InventoryEntity> productEntities) {
        return productEntities.stream()
                .map(Inventory::new)
                .collect(Collectors.toList());
    }

    @Override
    public Inventory addNewProduct(Inventory inventory) {
        InventoryEntity inventoryProduct = getProductsEntityFromInventory(inventory);
        InventoryEntity savedProduct = inventoryRepository.save(inventoryProduct);
        return getProductFromEntity(savedProduct);
    }

    @Override
    public Response deleteProductFromInventory(Long productId) {
        Optional<InventoryEntity> productOptional = inventoryRepository.findByProductId(productId);
        if (productOptional.isPresent()) {
            inventoryRepository.delete(productOptional.get());
            return Response.SUCCESS;
        }
        return Response.FAILED;
    }

    private Inventory getProductFromEntity(InventoryEntity savedProduct) {
        return new Inventory(savedProduct);
    }

    private InventoryEntity getProductsEntityFromInventory(Inventory inventory) {
        return InventoryEntity.builder()
                .productId(inventory.getProductId())
                .availableQuantity(inventory.getAvailableQuantity())
                .price(inventory.getPrice())
                .productDescription(inventory.getProductDescription())
                .build();
    }
}

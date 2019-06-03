package com.amitnteny.ecommerce.service;

import com.amitnteny.ecommerce.domain.Inventory;
import com.amitnteny.ecommerce.enums.Response;

import java.util.List;

public interface InventoryService {

    List<Inventory> getAllProducts();

    Inventory addNewProduct(Inventory inventoryProduct);

    Response deleteProductFromInventory(Long productId);
}

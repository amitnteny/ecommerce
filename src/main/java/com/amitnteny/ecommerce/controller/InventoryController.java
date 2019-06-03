package com.amitnteny.ecommerce.controller;

import com.amitnteny.ecommerce.domain.Inventory;
import com.amitnteny.ecommerce.enums.Response;
import com.amitnteny.ecommerce.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/getAllProducts")
    @ResponseBody
    public List<Inventory> getAllProducts() {
        return inventoryService.getAllProducts();
    }

    @PostMapping("/addNewProduct")
    @ResponseBody
    public Inventory addNewProduct(@RequestBody Inventory inventoryProduct) {
        return inventoryService.addNewProduct(inventoryProduct);
    }

    @DeleteMapping("/deleteProductFromInventory/{productId}")
    @ResponseBody
    public Response deleteProductFromInventory(@PathVariable(value = "productId") Long productId) {
        return inventoryService.deleteProductFromInventory(productId);
    }
}

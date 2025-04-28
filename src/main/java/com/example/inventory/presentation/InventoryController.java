package com.example.inventory.presentation;

import com.example.inventory.entity.Product;
import com.example.inventory.service.InventoryService;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    @GetMapping("/productExist")
    public ResponseEntity<Void> isProductExist(@RequestParam(name = "id") Integer id, @RequestParam(name = "ProductName") String name){
        boolean result = inventoryService.isProductExist(id, name);
        if (result)
            return ResponseEntity.ok().build(); // 200
        else
            return ResponseEntity.notFound().build(); // 404

    }

    @PostMapping("/reserveProduct")
    public ResponseEntity<Product> reserveProduct(@RequestParam(name = "productId") int id,@RequestParam(name = "productQuantity") int quantity){
        return ResponseEntity.ok(inventoryService.reserveProduct(id, quantity));
    }


}

package com.example.inventory.service;

import com.example.inventory.dto.ProductRequestDto;
import com.example.inventory.entity.Product;
import com.example.inventory.repository.InventoryRepository;
import com.example.inventory.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository, ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    public boolean isProductExist(Integer id, String name) {
        return productRepository.existsByIdAndName(id, name);
    }

    public Product reserveProduct(int id, int quantity) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        if (product.getQuantity() - quantity >= 0)
            product.setQuantity(product.getQuantity() - quantity);
        else
            try {
                throw new BadRequestException("Quantity ordered is more than stored");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }

        return productRepository.save(product);
    }



    // update stock if the order is updated (addition or subtraction)


}

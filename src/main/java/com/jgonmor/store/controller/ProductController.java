package com.jgonmor.store.controller;

import com.jgonmor.store.model.Product;
import com.jgonmor.store.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.status(404)
                                 .body("Product not found");
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping("/products/new")
    public ResponseEntity<?> saveProduct(@RequestBody Product product) {
        Product newProduct = productService.saveProduct(product);
        return ResponseEntity.ok(newProduct);
    }

    @PutMapping("/products/update")
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/products/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        Boolean deleted = productService.deleteProduct(id);

        if (deleted) {
            return ResponseEntity.status(200)
                                 .body("Product removed successfully");
        }

        return ResponseEntity.status(404)
                             .body("Product not found");
    }
}

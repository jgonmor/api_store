package com.jgonmor.store.controller;

import com.jgonmor.store.model.Product;
import com.jgonmor.store.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Product Controller to manage Products.
 *
 * @author Juanma G. Morcillo
 * @version 1.0
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    /**
     * Gets all Products
     *
     * @return A Response with a List of Products.
     */
    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * Finds a Product by id.
     *
     * @return A Response with a Product if it's found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.status(404)
                                 .body("Product not found");
        }
        return ResponseEntity.ok(product);
    }

    /**
     * Creates a Product
     *
     * @param product to create.
     * @return A Response with the product created.
     */
    @PostMapping("/new")
    public ResponseEntity<?> saveProduct(@RequestBody Product product) {
        Product newProduct = productService.saveProduct(product);
        return ResponseEntity.ok(newProduct);
    }

    /**
     * Updates a Product.
     *
     * @param product The Product to be updated.
     * @return A Response with the updated Product.
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(product);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Deletes a Product by an id.
     *
     * @param id The id of the Product to be deleted.
     * @return A Response indicating if the Product was deleted successfully.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        Boolean deleted = productService.deleteProduct(id);

        if (deleted) {
            return ResponseEntity.status(200)
                                 .body("Product removed successfully");
        }

        return ResponseEntity.status(404)
                             .body("Product not found");
    }

    /**
     * Gets Products where stock is lower than 5
     *
     * @return A Response with a List of Products with low stock.
     */
    @GetMapping("/low_stock")
    public ResponseEntity<?> getLowStockProducts() {
        List<Product> lowStockProducts = productService.getLowStockProducts();
        return ResponseEntity.ok(lowStockProducts);
    }

}

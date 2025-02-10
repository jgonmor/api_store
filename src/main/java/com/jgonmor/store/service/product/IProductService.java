package com.jgonmor.store.service.product;

import com.jgonmor.store.model.Product;

import java.util.List;

public interface IProductService {

    /**
     * Gets all Products
     *
     * @return A List of Products.
     */
    List<Product> getAllProducts();

    /**
     * Finds a product by an id.
     *
     * @param id The id of the Product to be found.
     * @return A Product.
     */
    Product getProductById(Long id);

    /**
     * Creates a new Product.
     *
     * @param product The Product to be created.
     * @return The new Product.
     */
    Product saveProduct(Product product);

    /**
     * Deletes a Product by an id.
     *
     * @param id The id of the Product to be deleted.
     * @return A Boolean indicating if the Product was deleted successfully.
     */
    Boolean deleteProduct(Long id);

    /**
     * Updates a Product.
     *
     * @param product The Product to be updated.
     * @return The updated Product.
     */
    Product updateProduct(Product product);

    /**
     * Gets Products where stock is lower than 5
     *
     * @return A List of Products with low stock.
     */
    List<Product> getLowStockProducts();

    /**
     * Creates a new Product for each product in the list.
     *
     * @param productList The List of Products to be created.
     * @return A List of the new Products.
     */
    List<Product> saveProducts(List<Product> productList);

}

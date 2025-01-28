package com.jgonmor.store.service.product;

import com.jgonmor.store.model.Product;

import java.util.List;

public interface IProductService {

    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product saveProduct(Product product);

    Boolean deleteProduct(Long id);

    Product updateProduct(Product product);

    List<Product> getLowStockProducts();

    void saveProducts(List<Product> productList);

    void existOrException(Long id);
}

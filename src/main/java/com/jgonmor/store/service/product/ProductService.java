package com.jgonmor.store.service.product;

import com.jgonmor.store.exceptions.EmptyQueryException;
import com.jgonmor.store.exceptions.EmptyTableException;
import com.jgonmor.store.exceptions.ResourceNotFoundException;
import com.jgonmor.store.model.Product;
import com.jgonmor.store.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Product Service Interface
 * Contains methods to manage products.
 *
 * @author Juanma G. Morcillo
 * @version 1.0
 */
@Service
@Primary
public class ProductService implements IProductService {


    @Autowired
    private IProductRepository productRepository;

    /**
     * Checks if the product exists.
     *
     * @param id The id of the product to be checked.
     */
    public void existOrException(Long id){
        if(!productRepository.existsById(id)){
            throw new ResourceNotFoundException("Product not found");
        }
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();

        if(products.isEmpty()){
            throw new EmptyTableException("There are no products");
        }

        return products;
    }

    @Override
    public Product getProductById(Long id) {

        return productRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Boolean deleteProduct(Long id) {

        this.existOrException(id);

        productRepository.deleteById(id);

        return true;
    }

    @Override
    public Product updateProduct(Product product) {
        this.existOrException(product.getId());
        return this.saveProduct(product);
    }

    @Override
    public List<Product> getLowStockProducts() {
        List<Product> products = productRepository.findLowStockProducts();
        if(products.isEmpty()){
            throw new EmptyQueryException("There are no products with low stock");
        }
        return products;
    }

    @Override
    public List<Product> saveProducts(List<Product> productList) {
        return productRepository.saveAll(productList);
    }

}

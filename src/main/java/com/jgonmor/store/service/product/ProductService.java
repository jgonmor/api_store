package com.jgonmor.store.service.product;

import com.jgonmor.store.model.Product;
import com.jgonmor.store.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Boolean deleteProduct(Long id) {

        if(this.getProductById(id) == null){
            return false;
        }

        productRepository.deleteById(id);

        return true;
    }

    @Override
    public Product updateProduct(Product product) {
        return this.saveProduct(product);
    }
}

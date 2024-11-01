package org.project.ecommerce.service;

import org.project.ecommerce.models.Product;
import org.project.ecommerce.repository.ProductRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService{
    private final ProductRepository productRepository;


    ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(){

           return productRepository.findAll();
    }
    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

    public List<Product> getProductsFiltered(@Param ("category") String category){
        return productRepository.getProductsFiltered(category);
    }

    public void saveProduct(Product product){
        productRepository.save(product);
    }
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
}

package org.project.ecommerce.service;

import jakarta.transaction.Transactional;
import org.project.ecommerce.models.Product;
import org.project.ecommerce.models.User;
import org.project.ecommerce.repository.ProductRepository;
import org.project.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService{
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    ProductService(ProductRepository productRepository, UserRepository userRepository){
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

    public void saveProduct(Product product){
        productRepository.save(product);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    public Page<Product> getFilteredProducts(Integer page, Integer size, String direction, String orderBy) {
        Pageable pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction.toUpperCase()), orderBy);
        return productRepository.findAll(pageRequest);
    }

    public Page<Product> getProductsByFilterPaginated(
            String category,String price, Integer page, Integer size, String direction, String orderBy) {
        Sort sort;
        if ("low".equalsIgnoreCase(price)) {
            sort = Sort.by(Sort.Direction.ASC, "price");
        }
        else if ("high".equalsIgnoreCase(price)) {
            sort = Sort.by(Sort.Direction.DESC, "price");
        }else{
            sort = Sort.by(Sort.Direction.valueOf(direction.toUpperCase()), orderBy);
        }
        Pageable pageRequest = PageRequest.of(page, size , sort);
        return productRepository.getProductsFilteredByCategory(category, pageRequest);
    }

    @Transactional
    public void addProductToFavorite(Product product, User user) {
            user = userRepository.findById(user.getId()).orElse(user);
        if (!user.getFavoriteProducts().contains(product)) {
            user.getFavoriteProducts().add(product);
            userRepository.save(user);
        }else if(user.getFavoriteProducts().contains(product)){
            throw new RuntimeException("Product is already added");
        }
    }

    public Product getProductByIdOrThrow(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            return productOptional.get();
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    public Set<Product> getFavoriteProducts(User user) {
        return userRepository.findById(user.getId())
                .map(User::getFavoriteProducts)
                .orElse(Collections.emptySet());
    }
}

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
import java.util.List;
import java.util.Optional;

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
    public void addProductToFavorite(Optional<Product> optionalProduct, User user) {
        if(optionalProduct.isPresent()){
            List<Product> favoriteProducts = user.getFavoriteProducts();
            Product product = optionalProduct.get();
            //dodac ze jesli produkt jest juz w ulubionych u zalogowanego uzytkownika to nie mozna dodac drugi raz
            favoriteProducts.add(product);
            user.setFavoriteProducts(favoriteProducts);
            userRepository.save(user);
        }else{
            throw new RuntimeException("Product dont exist");
        }
    }
}

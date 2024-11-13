package org.project.ecommerce.service;

import org.project.ecommerce.models.Product;
import org.project.ecommerce.repository.ProductRepository;
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

    ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(){
           return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

//    public List<Product> getProductsByFilter(@Param ("category") String category, @Param ("price") String price){
//        if (price.equals("low")) {
//            return productRepository.getProductsFiltered(category,Sort.by(Sort.Direction.ASC, "price"));
//        }
//        if (price.equals("high")) {
//            return productRepository.getProductsFiltered(category,Sort.by(Sort.Direction.DESC, "price"));
//        }else{
//            return productRepository.getProductsFiltered(category, null);
//        }
//    }
    public void saveProduct(Product product){
        productRepository.save(product);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    public Page<Product> getFilteredProducts(Integer page, Integer size, String direction, String orderBy){
        Pageable pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction.toUpperCase()), orderBy);
        Page<Product> foundProducts = productRepository.findAll(pageRequest);
        return foundProducts;
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
}

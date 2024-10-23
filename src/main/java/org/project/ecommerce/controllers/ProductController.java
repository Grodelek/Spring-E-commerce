package org.project.ecommerce.controllers;
import org.project.ecommerce.models.Product;
import org.project.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
    this.productService = productService;
    }

    @GetMapping
    public String home(){
        return "homepage";
    }

    @GetMapping("/products")
    public String getProducts(Model model){
        List<Product> allProducts = productService.getAllProducts();
        model.addAttribute("products", allProducts);
        return "products";
    }

    @GetMapping("/products/{id}")
    public String getProductById(@PathVariable Long id, Model model){
        Optional<Product> product = productService.getProductById(id);
        if(product.isPresent()){
            model.addAttribute("product", product.get());
            return "productInfo";
        }else{
            return "homepage";
        }
    }

    @GetMapping("/add")
    public String addProduct(Model model){
        model.addAttribute("product", new Product());
        return "addProduct";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id, Model model){
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            productService.deleteProduct(id);
            return "redirect:/products";
        }else{
            return "productInfo";
        }
    }

    @PostMapping("/add")
    public String submitProduct(@ModelAttribute ("product") Product product){
            productService.saveProduct(product);
            return "redirect:/products";
    }



}

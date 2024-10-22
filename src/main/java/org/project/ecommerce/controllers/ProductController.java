package org.project.ecommerce.controllers;
import org.project.ecommerce.models.Product;
import org.project.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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

    @GetMapping("/add")
    public String addProduct(Model model){
        model.addAttribute("product", new Product());
        return "addProduct";
    }

    @PostMapping("/add")
    public String submitProduct(@ModelAttribute ("product") Product product){
            productService.saveProduct(product);
            return "redirect:/products";
    }


}

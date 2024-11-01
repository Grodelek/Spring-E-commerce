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
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
    this.productService = productService;
    }

    @GetMapping()
    public String getProducts(Model model){
        List<Product> allProducts = productService.getAllProducts();
        model.addAttribute("products", allProducts);
        return "products/allProducts";
    }

    @GetMapping("/filtered")
    public String getProductsFilter(Model model, @RequestParam String category){
        List<Product> filteredProducts = productService.getProductsFiltered(category);
        model.addAttribute("products", filteredProducts);
        if(category.equals("all")){
            return "redirect:/products";
        }
        return "products/onlyFiltered";
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable Long id, Model model){
        Optional<Product> product = productService.getProductById(id);
        if(product.isPresent()){
            model.addAttribute("product", product.get());
            return "products/productInfo";
        }else{
            return "homepage";
        }
    }

    @GetMapping("/add")
    public String addProduct(Model model){
        model.addAttribute("product", new Product());
        return "products/addProduct";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            productService.deleteProduct(id);
            return "redirect:/products";
        }else{
            return "products/productInfo";
        }
    }

    @GetMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, Model model){
        Optional<Product> product = productService.getProductById(id);
        if(product.isPresent()){
            model.addAttribute("product", product.get());
            return "products/updateProduct";
        }else{
            return "products/productInfo";
        }
    }

    @PostMapping("/update/{id}")
    public String submitUpdate(@PathVariable Long id, @ModelAttribute ("product") Product product) {
        productService.saveProduct(product);
        return "redirect:/products";
    }
    
    @PostMapping("/add")
    public String submitProduct(@ModelAttribute ("product") Product product){
            productService.saveProduct(product);
            return "redirect:/products";
    }


}

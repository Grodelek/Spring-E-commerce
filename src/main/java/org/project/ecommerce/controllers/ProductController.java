package org.project.ecommerce.controllers;
import org.project.ecommerce.models.Product;
import org.project.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String getProducts(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "4") Integer size,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(defaultValue = "name") String orderBy,
                              Model model){
        Page<Product> productsPage = productService.getFilteredProducts(page, size, direction, orderBy);
        model.addAttribute("productsPage", productsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productsPage.getTotalPages());
        model.addAttribute("totalItems", productsPage.getTotalElements());
        return "products/allProducts";
    }

    @GetMapping("/filtered")
    public String getProductsFilter(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String price,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "4") Integer size,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(defaultValue = "name") String orderBy,
            Model model){
        Page<Product> productsPage = productService.getProductsByFilterPaginated(category,price,page,size,direction,orderBy);
        model.addAttribute("productsPage", productsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productsPage.getTotalPages());
        model.addAttribute("totalItems", productsPage.getTotalElements());
        model.addAttribute("category", category);
        model.addAttribute("price", price);

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

    @GetMapping("/manage/{id}")
    public String manage(@PathVariable Long id, Model model){
        Optional<Product> product = productService.getProductById(id);
        if(product.isPresent()){
            model.addAttribute("product", product.get());
        }else{
            return "redirect:/products";
        }
        return "products/manageProduct";
    }
}

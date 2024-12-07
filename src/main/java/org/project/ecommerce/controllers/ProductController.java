package org.project.ecommerce.controllers;
import org.project.ecommerce.models.Product;
import org.project.ecommerce.models.User;
import org.project.ecommerce.service.ProductService;
import org.project.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;
import java.util.Set;


@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping()
    public String getProducts(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "4") Integer size,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(defaultValue = "name") String orderBy,
            Model model) {
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
            Model model) {
        Page<Product> productsPage = productService.getProductsByFilterPaginated(category, price, page, size, direction, orderBy);
        model.addAttribute("productsPage", productsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productsPage.getTotalPages());
        model.addAttribute("totalItems", productsPage.getTotalElements());
        model.addAttribute("category", category);
        model.addAttribute("price", price);

        return "products/onlyFiltered";
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "products/productInfo";
        } else {
            return "homepage";
        }
    }

    @GetMapping("/add")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        return "products/addProduct";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            productService.deleteProduct(id);
            return "redirect:/products";
        } else {
            return "products/productInfo";
        }
    }

    @GetMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "products/updateProduct";
        } else {
            return "products/productInfo";
        }
    }

    @PostMapping("/update/{id}")
    public String submitUpdate(@PathVariable Long id, @ModelAttribute("product") Product product) {
        productService.saveProduct(product);
        return "redirect:/products";
    }

    @PostMapping("/add")
    public String submitProduct(@ModelAttribute("product") Product product) {
        productService.saveProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/manage/{id}")
    public String manage(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
        } else {
            return "redirect:/products";
        }
        return "products/manageProduct";
    }

    @GetMapping("/favorite")
    public String getFavoriteProducts(@AuthenticationPrincipal User loggedInUser, Model model) {
        Set<Product> favoriteProductList = productService.getFavoriteProducts(loggedInUser);

        model.addAttribute("products", favoriteProductList);
        return "products/favorite";
    }

    @PostMapping("/favorite/{id}")
    public String addToFavorite(@PathVariable Long id, @AuthenticationPrincipal User loggedInUser, RedirectAttributes redirectAttributes) {
            Optional<Product> foundProduct = productService.getProductById(id);
           try {
               if (foundProduct.isPresent()) {
                       productService.addProductToFavorite(foundProduct.get(), loggedInUser);
                       redirectAttributes.addFlashAttribute("favorite", "Product added to favorites.");
                       return "redirect:/products";
                   }
           }catch(RuntimeException ex){
               redirectAttributes.addFlashAttribute("error", "Error product already in favorites.");
               return "redirect:/products";
           }
        return "redirect:/products";
    }
}




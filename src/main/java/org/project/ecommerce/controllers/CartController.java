package org.project.ecommerce.controllers;

import jakarta.transaction.Transactional;
import org.project.ecommerce.models.Cart;
import org.project.ecommerce.models.CartProducts;
import org.project.ecommerce.models.Product;
import org.project.ecommerce.models.User;
import org.project.ecommerce.service.CartProductsService;
import org.project.ecommerce.service.CartService;
import org.project.ecommerce.service.ProductService;
import org.project.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.Optional;


@Controller
public class CartController {
    private final CartService cartService;
    private final UserService userService;
    private final CartProductsService cartProductsService;
    private final ProductService productService;

    @Autowired
    public CartController(CartService cartService, UserService userService, CartProductsService cartProductsService, ProductService productService) {
        this.cartService = cartService;
        this.userService = userService;
        this.cartProductsService = cartProductsService;
        this.productService = productService;
    }

    @GetMapping("/cart")
    public String getCart(Model model, @AuthenticationPrincipal User loggedInUser) {
        Cart cart = loggedInUser.getCart();
        if (cart == null) {
            cart = cartService.createCartForUser(loggedInUser);
            loggedInUser.setCart(cart);
            userService.save(loggedInUser);
        }
        model.addAttribute("user", loggedInUser);
        List<CartProducts> cartItemList = cartProductsService.getCartItemsByCart(cart);
        model.addAttribute("products", cartItemList);
        return "cart/cartHome";
    }

    @Transactional
    @GetMapping("/cart/{id}")
    public String addToCart(@PathVariable Long id, @AuthenticationPrincipal User loggedInUser){
        Optional<Product> product = productService.getProductById(id);
        if(product.isEmpty()){
            return "redirect:/products";
        }
        Product foundProduct = product.get();
        Cart cart = loggedInUser.getCart();
        if(cart == null) {
            cart = cartService.createCartForUser(loggedInUser);
            loggedInUser.setCart(cart);
            userService.save(loggedInUser);
        }
        Optional<CartProducts> cartProduct = cartProductsService.findByProductIdAndCartId(id,cart.getId());
        if(cartProduct.isPresent()){
            CartProducts existingproduct = cartProduct.get();
            existingproduct.setQuantity(existingproduct.getQuantity()+1);
            cartProductsService.save(existingproduct);
        }else {
            CartProducts newCartProduct = new CartProducts();
            newCartProduct.setProduct(foundProduct);
            newCartProduct.setCart(cart);
            newCartProduct.setQuantity(1);
            cartProductsService.save(newCartProduct);
        }
            return "success";

    }
}


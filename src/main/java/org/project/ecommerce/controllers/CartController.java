package org.project.ecommerce.controllers;

import jakarta.transaction.Transactional;
import org.project.ecommerce.models.Cart;
import org.project.ecommerce.models.CartItem;
import org.project.ecommerce.models.Product;
import org.project.ecommerce.models.User;
import org.project.ecommerce.service.CartItemService;
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
    private final CartItemService cartItemService;
    private final ProductService productService;

    @Autowired
    public CartController(CartService cartService, UserService userService, CartItemService cartItemService, ProductService productService) {
        this.cartService = cartService;
        this.userService = userService;
        this.cartItemService = cartItemService;
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
        model.addAttribute("cart", loggedInUser.getCart());
        return "cart/cartHome";
    }

    @Transactional
    @GetMapping("/cart/{id}")
    public String addToCart(@PathVariable Long id, Model model, @AuthenticationPrincipal User loggedInUser){
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
        CartItem cartItem = new CartItem();
        cartItem.setProduct(foundProduct);
        cartItem.setCart(cart);
        cartItem.setQuantity(1);
        cartItemService.save(cartItem);
        List<CartItem> cartItemList = cartItemService.getCartItemsByCart(cart);
        model.addAttribute("products", cartItemList);
        return "cart/cartHome";
    }
}


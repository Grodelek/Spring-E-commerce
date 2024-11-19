package org.project.ecommerce.controllers;


import jakarta.transaction.Transactional;
import org.project.ecommerce.models.Cart;
import org.project.ecommerce.models.User;
import org.project.ecommerce.service.CartService;
import org.project.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    @Autowired
    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @Transactional
    @GetMapping("/cart")
    public String getOnetoOne(Model model, @AuthenticationPrincipal User loggedInUser) {
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



}

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Optional;


@Controller
public class CartController {
    private final CartService cartService;
    private final UserService userService;
    private final CartProductsService cartProductsService;
    private final ProductService productService;

    @Autowired
    public CartController(
            CartService cartService,
            UserService userService,
            CartProductsService cartProductsService,
            ProductService productService){
        this.cartService = cartService;
        this.userService = userService;
        this.cartProductsService = cartProductsService;
        this.productService = productService;
    }

    @GetMapping("/cart")
    public String getCart(Model model, @AuthenticationPrincipal User loggedInUser) {
        double cartPrice = addCartPrice(loggedInUser);
        Cart cart = loggedInUser.getCart();
        if (cart == null) {
            cart = cartService.createCartForUser(loggedInUser);
            loggedInUser.setCart(cart);
            userService.save(loggedInUser);
        }
        List<CartProducts> cartItemList = cartProductsService.getCartItemsByCart(cart);
        model.addAttribute("user", loggedInUser);
        model.addAttribute("products", cartItemList);
        model.addAttribute("cartPrice", cartPrice);
        return "cart/cartHome";
    }

    public double addCartPrice(@AuthenticationPrincipal User loggedInUser){
        double cartPrice = 0;
        Cart cart = loggedInUser.getCart();
        List<CartProducts> cartItemList = cartProductsService.getCartItemsByCart(cart);
        for(CartProducts cartItem :  cartItemList){
            cartPrice += cartItem.getQuantity() * cartItem.getProduct().getPrice();
        }
        return cartPrice;
    }

    @Transactional
    @GetMapping("/cart/{id}")
    public String addToCart(
            @PathVariable Long id,
            @AuthenticationPrincipal User loggedInUser,
            RedirectAttributes redirectAttributes){
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
            if(foundProduct.getQuantityInStock() > existingproduct.getQuantity()) {
                existingproduct.setQuantity(existingproduct.getQuantity() + 1);
                cartProductsService.save(existingproduct);
            }else{
                redirectAttributes.addFlashAttribute("message", "Product is not in stock!");
                return "redirect:/products";
            }
        }else {
            CartProducts newCartProduct = new CartProducts();
            newCartProduct.setProduct(foundProduct);
            newCartProduct.setCart(cart);
            newCartProduct.setQuantity(1);
            cartProductsService.save(newCartProduct);
        }
            return "redirect:/cart";
    }

    @Transactional
    @GetMapping("/cart/lower/{id}")
    public String substractQuantity(@PathVariable Long id, @AuthenticationPrincipal User loggedInUser){
       Cart cart = loggedInUser.getCart();
        Optional<CartProducts> cartProduct = cartProductsService.findByProductIdAndCartId(id,cart.getId());
        if(cartProduct.isEmpty()){
            return "redirect:/cart";
        }
        CartProducts products = cartProduct.get();
        if(products.getQuantity() > 1){
            products.setQuantity(products.getQuantity() - 1);
            cartProductsService.save(products);
            return "redirect:/cart";
        }
        if(products.getQuantity() == 1){
            products.setQuantity(0);
            cartProductsService.delete(products);
            return "redirect:/cart";
        }
        return "redirect:/products";
    }

    @Transactional
    @GetMapping("/cart/add/{id}")
    public String addQuantity(@PathVariable Long id,
                              @AuthenticationPrincipal User loggedInUser,
                              RedirectAttributes redirectAttributes){
        Cart cart = loggedInUser.getCart();
        Optional<CartProducts> cartProductOpt = cartProductsService.findByProductIdAndCartId(id,cart.getId());
        if(cartProductOpt.isEmpty()){
            return "redirect:/cart";
        }
        CartProducts cartProducts = cartProductOpt.get();
        Product product = cartProducts.getProduct();
            if (cartProducts.getQuantity() != product.getQuantityInStock()) {
                cartProducts.setQuantity(cartProducts.getQuantity() + 1);
                cartProductsService.save(cartProducts);
                return "redirect:/cart";
            }else{
                redirectAttributes.addFlashAttribute("error", "Cannot increase quantity. Stock limit reached or product not in cart.");
                return "redirect:/cart";
            }
        }
}


package org.project.ecommerce.service;

import org.project.ecommerce.models.Cart;
import org.project.ecommerce.models.CartItem;
import org.project.ecommerce.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public void save(CartItem cartItem){
        cartItemRepository.save(cartItem);
    }

    public List<CartItem> getCartItemsByCart(Cart cart) {
        return cartItemRepository.findByCart(cart);
    }
}

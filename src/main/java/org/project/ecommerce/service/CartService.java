package org.project.ecommerce.service;

import jakarta.transaction.Transactional;
import org.project.ecommerce.models.Cart;
import org.project.ecommerce.models.User;
import org.project.ecommerce.repository.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {
  private final CartRepository cartRepository;

  public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

  @Transactional
  public Cart createCartForUser(User user){
      Cart cart = new Cart();
      cart.setUser(user);
      return cartRepository.save(cart);
    }
}

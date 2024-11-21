package org.project.ecommerce.service;

import org.project.ecommerce.models.Cart;
import org.project.ecommerce.models.CartProducts;
import org.project.ecommerce.repository.CartProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CartProductsService {
    private final CartProductsRepository cartProductsRepository;

    @Autowired
    public CartProductsService(CartProductsRepository cartProductsRepository) {
        this.cartProductsRepository = cartProductsRepository;
    }

    public void save(CartProducts cartProducts){
        cartProductsRepository.save(cartProducts);
    }

    public List<CartProducts> getCartItemsByCart(Cart cart) {
        return cartProductsRepository.findByCart(cart);
    }

    public Optional<CartProducts> findByProductIdAndCartId(Long productId, Long cartId){
        return cartProductsRepository.findByProductIdAndCartId(productId, cartId);
    }

}

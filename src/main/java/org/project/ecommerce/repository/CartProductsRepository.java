package org.project.ecommerce.repository;

import org.project.ecommerce.models.Cart;
import org.project.ecommerce.models.CartProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartProductsRepository extends JpaRepository<CartProducts, Long> {
    List<CartProducts> findByCart(Cart cart);
    Optional<CartProducts> findByProductIdAndCartId(Long productId, Long cartId);
}

package org.project.ecommerce.repository;

import org.project.ecommerce.models.Cart;
import org.project.ecommerce.models.CartProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartProductsRepository extends JpaRepository<CartProducts, Long> {
    List<CartProducts> findByCart(Cart cart);
}

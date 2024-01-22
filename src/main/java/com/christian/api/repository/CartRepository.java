package com.christian.api.repository;

import com.christian.api.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {


//    Cart findCartByEmailAndCartId(String email, Long cartId);
//    List<Cart> findCartsByProductId(Long productId);
}

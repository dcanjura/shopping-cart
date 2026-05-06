package repository;

import domain.Cart;
import domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, String> {

    Cart createCart(String userId);

    Cart getCartByUser(String userId);

    Cart getCartById(Long id);
}

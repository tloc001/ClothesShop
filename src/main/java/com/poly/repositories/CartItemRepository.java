package com.poly.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poly.models.CartItem;
import com.poly.models.ProductVariantValue;

import jakarta.transaction.Transactional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
	@Query("FROM CartItem ci WHERE ci.cart.username = ?1 ")
	List<CartItem> findAllItemByUsername(String username);
	@Query("FROM CartItem ci WHERE ci.cart.username = ?1 AND ci.pvv.id = ?2")
	CartItem findCartItem(String username,Integer idPvv);
	@Transactional
	@Modifying
	@Query("DELETE FROM CartItem ci WHERE ci.cart.username = ?1 AND ci.id = ?2")
	void deleteCartItem(String username,Integer idItem);
	@Transactional
	@Modifying
	@Query("UPDATE CartItem ci SET ci.pvv = NULL WHERE ci.pvv.id = ?1 ")
	void updateCartItemWhenDeleteIdPvv(Integer idPvv);
	@Transactional
	@Modifying
	@Query("UPDATE CartItem ci SET ci.pvv = NULL WHERE ci.pvv.product.id = ?1 ")
	void updateCartItemWhenDeleteIdPro(Integer idPro);

}

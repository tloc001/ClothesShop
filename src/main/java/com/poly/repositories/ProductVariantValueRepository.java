package com.poly.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poly.models.ProductVariantValue;

import jakarta.transaction.Transactional;

@Repository
public interface ProductVariantValueRepository extends JpaRepository<ProductVariantValue, Integer> {
	@Query("FROM ProductVariantValue pvv WHERE pvv.product.id = ?1")
	List<ProductVariantValue> findAllPvvByIdProduct(Integer id);
	@Query("SELECT pvv.sku FROM ProductVariantValue pvv WHERE pvv.product.id =?1")
	String[] listSkuByIdProduct(Integer id);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM ProductVariantValue pvv WHERE pvv.product.id = ?1")
	void DeleteAllProductById(Integer id);
	@Query("SELECT pvv.stock FROM ProductVariantValue pvv WHERE pvv.id = ?1")
	Integer checkStockByIdPvv(Integer idPvv);
	@Query("SELECT ci.pvv FROM CartItem ci WHERE ci.pvv.id = ?1 AND ci.cart.username = ?2")
	ProductVariantValue findPvvFromCartItemById(Integer idPvv, String username);
	@Query("SELECT product.id, SUM(pvv.turnbuy) FROM ProductVariantValue pvv GROUP BY pvv.product.id ")
	List<Integer[]> findTurnBuyByProductId();
	
}


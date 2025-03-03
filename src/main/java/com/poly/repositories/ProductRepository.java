package com.poly.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poly.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	@Query("FROM Product p WHERE p.name = ?1 ")
	Product findByNameProduct(String category);
	
	@Query("SELECT p.id,"
			+ "p.image,"
			+ "p.category.name,"
			+ "p.name,"
			+ "p.turnbuy,"
			+ " count(pvv.product.id),"
			+ "  sum(pvv.stock)"
			+ " FROM Product p INNER JOIN ProductVariantValue pvv ON "
			+ "p.id = pvv.product.id "
			+ "GROUP BY p.id,p.image,p.name,p.category.name,"
			+ "p.turnbuy")
	List<String[]> findName();
	
	@Query("SELECT p FROM Product p WHERE p.turnbuy > 0 ORDER BY p.turnbuy DESC")
		List<Product> top5HotProduct(Pageable pageable);

	@Query("SELECT pvv.product.id, pvv.product.name, sum(oi.total) FROM OrderItem oi GROUP BY pvv.product.id,pvv.product.name ORDER BY SUM(oi.total) ASC")
	List<Object[]> revenueProduct();
	@Query("SELECT "
			+ "p.id,"
			+ " p.image,"
			+ " p.name,"
			+ "p.category.name,"
			+ "p.turnbuy,"
			+ "SUM(pvv.stock),"
			+ "MIN(pvv.price),"
			+ "MAX(pvv.price),"
			+ "MAX(pvv.oldprice)"
			+ " FROM Product p INNER JOIN ProductVariantValue pvv ON p.id = pvv.product.id WHERE p.category.name LIKE %?1% AND p.name LIKE %?2% GROUP BY "
			+ "p.id, p.image, p.name, p.category.name, p.turnbuy")
	Page<String[]> fillPublic(Pageable pageable,String category,String search);
	
	@Query("SELECT "
			+ "p.id,"
			+ " p.image,"
			+ " p.name,"
			+ "p.category.name,"
			+ "p.turnbuy,"
			+ "SUM(pvv.stock),"
			+ "MIN(pvv.price),"
			+ "MAX(pvv.price),"
			+ "MAX(pvv.oldprice),"
			+ "p.description"
			+ " FROM Product p INNER JOIN ProductVariantValue pvv ON p.id = pvv.product.id WHERE pvv.product.id = ?1 GROUP BY "
			+ "p.id, p.image, p.name, p.category.name, p.turnbuy,p.description")
	List<String[]> findDetailsProduct(Integer id);
	
}


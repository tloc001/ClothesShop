package com.poly.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poly.models.ProductValue;

@Repository
public interface ProductValueRepository extends JpaRepository<ProductValue, Integer> {
	@Query("SELECT pa.value FROM ProductValue pa WHERE pa.attribute.attributeId = ?1 GROUP BY pa.value")
	List<String> findAllCategory(Integer category);
}

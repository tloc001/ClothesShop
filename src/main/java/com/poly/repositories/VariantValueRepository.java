package com.poly.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poly.models.Variant;
import com.poly.models.VariantValue;

@Repository
public interface VariantValueRepository extends JpaRepository<VariantValue, Integer>{
	@Query("FROM VariantValue vv WHERE vv.value = ?1")
	VariantValue findByValueVariantV(String value);
	@Query("SELECT vv.value FROM VariantValue vv WHERE vv.id = ?1")
	String values(Integer id);
}

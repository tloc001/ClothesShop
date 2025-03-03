package com.poly.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poly.models.Variant;

@Repository
public interface VariantRepository extends JpaRepository<Variant, Integer> {
@Query("FROM Variant va WHERE va.name = ?1")
Variant findByNameVariant(String variantName);
@Query("SELECT vv.variant FROM VariantValue vv WHERE vv.id = ?1")
Variant findVariantByIdPvv(Integer id);
}

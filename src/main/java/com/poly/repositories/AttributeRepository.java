package com.poly.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poly.models.Attribute;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Integer> {
	@Query(" FROM Attribute a WHERE a.name = ?1")
	Attribute findByNameAt(String name);
}

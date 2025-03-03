package com.poly.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poly.models.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	@Query("FROM Category c WHERE c.name = ?1")
	Category findByNameCategory(String nameCategory);
	@Query("SELECT c.name FROM Category c INNER JOIN Product p ON c.id = p.category.id GROUP BY c.name")
	List<String> findCategoryExist();
}

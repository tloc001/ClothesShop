package com.poly.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.models.Category;
import com.poly.repositories.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository repository;

	public List<Category> findAllCategory() {
		return repository.findAll();
	}

	public Category checkExistCategory(String c) {
		if (repository.findByNameCategory(c) == null) {
			Category ca = new Category();
			ca.setName(c);
			return repository.save(ca);
		}
		return repository.findByNameCategory(c);
	}
	public List<String> categoryExist(){
		return repository.findCategoryExist();
	}

}

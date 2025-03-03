package com.poly.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.models.Attribute;
import com.poly.models.Product;
import com.poly.models.ProductValue;
import com.poly.repositories.ProductValueRepository;

@Service
public class ProductValueService {
	@Autowired
	ProductValueRepository productValueRepository;
	public ProductValue save (ProductValue pro) {
		return productValueRepository.save(pro);
	}
	public List<String> findAllCategory(Integer idOfCategory){
		return productValueRepository.findAllCategory(idOfCategory);
	}
}

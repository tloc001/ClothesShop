package com.poly.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.models.Variant;
import com.poly.repositories.VariantRepository;

@Service
public class VariantService {
	@Autowired
	VariantRepository repository;
	
	public Variant saveAndUpdate(Variant v) {		
		return repository.save(v);
	}
	
	public Variant checkExistVariant(String va) {
		if (repository.findByNameVariant(va)== null) {
			Variant v = new Variant();
			v.setName(va);
			return repository.save(v);
		}
		return repository.findByNameVariant(va);
	}
	public List<Variant> findAllVariant(){
		return repository.findAll();
		}
	public Variant findVariantByIdPvv(Integer id) {
		return repository.findVariantByIdPvv(id);
	}
	
	
}

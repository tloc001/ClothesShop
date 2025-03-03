package com.poly.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.poly.models.VariantValue;
import com.poly.repositories.VariantValueRepository;

@Service
public class VariantValueService {
	@Autowired
	VariantValueRepository repository;

	public VariantValue checkExistVariantValue(VariantValue vv) {
		
		if (repository.findByValueVariantV(vv.getValue()) == null) {
			
			repository.save(vv);
		}
		return repository.findByValueVariantV(vv.getValue());
	}
	public String value(Integer id) {
		return repository.values(id);
	}
}

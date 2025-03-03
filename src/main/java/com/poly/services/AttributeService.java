package com.poly.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.models.Attribute;
import com.poly.repositories.AttributeRepository;

@Service
public class AttributeService {
	@Autowired
	AttributeRepository attributeRepository;
	public Attribute findByNameAtt(String name) {
		return attributeRepository.findByNameAt(name);
	}
	public Attribute checkAndGetAttribute(String attribute) {
//		nếu kiểm tra kh có thì save and get ra
		if (attributeRepository.findByNameAt(attribute)== null) {
			Attribute a = new Attribute();
			a.setName(attribute);
			attributeRepository.save(a);
			return attributeRepository.findByNameAt(attribute);
		}
//		có rồi thì get ra
		return attributeRepository.findByNameAt(attribute);

	}
}

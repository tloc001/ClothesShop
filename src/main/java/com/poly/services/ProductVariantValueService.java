package com.poly.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.models.ProductVariantValue;
import com.poly.repositories.ProductRepository;
import com.poly.repositories.ProductVariantValueRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductVariantValueService {
	@Autowired
	ProductVariantValueRepository repository;
	@Autowired
	CartItemService cartItemService;
	public ProductVariantValue save(ProductVariantValue pvv) {
		return repository.save(pvv);
	}
	public List<ProductVariantValue> findAllPvv(){
		return repository.findAll();
	}
	public List<ProductVariantValue> findPvvByIdProduct(Integer id){
		return repository.findAllPvvByIdProduct(id);
	}
	public String[] listSkuByIdProduct(Integer id) {
		return repository.listSkuByIdProduct(id);
	}
	@Transactional
	public void deletePvv(Integer idPvv) {
		
				cartItemService.updateCartItemWhenDeletePvv(idPvv);
				repository.deleteById(idPvv);
			
	}
	public void deleteAllPvv(Integer id) {
		repository.DeleteAllProductById(id);
	}
	public ProductVariantValue findPvvByIdPvv(Integer idPvv) {
		try {
			repository.findById(idPvv).get();
			return 	repository.findById(idPvv).get();

		} catch (Exception e) {
			return new ProductVariantValue();
		}
	}
	
	public int checkStockByIdPvv(Integer idPvv) {
		return repository.checkStockByIdPvv(idPvv);
	}
	public ProductVariantValue findPvvFromCartItemById(Integer idPvv, String username) {
		return repository.findPvvFromCartItemById(idPvv, username);
		
	}
	
}

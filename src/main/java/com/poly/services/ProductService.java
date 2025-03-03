package com.poly.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poly.models.Cart;
import com.poly.models.Product;
import com.poly.models.User;
import com.poly.repositories.ProductRepository;

@Service
public class ProductService {
	@Autowired
	ProductRepository productRepository;
	@Autowired
	CartItemService cartItemService;
	@Autowired
	ProductVariantValueService productVariantValueService;
	public List<Product> findAllProduct(){
		return productRepository.findAll();		
	}
	public Product findByCategory(String nameProduct) {
		return productRepository.findByNameProduct(nameProduct);
	}

	public Product saveProduct(Product p) {	
//			nếu chưa thì lưu vào và get ra
			return	productRepository.save(p);

	
	}
	public List<String[]> findName(){
		return productRepository.findName();
	}
	
	public Product findProductById(Integer id) {
		return productRepository.findById(id).get();
	}
	@Transactional
	public void deleteProductById(Integer idPro) {
	
			cartItemService.updateCartItemWhenDeletePro(idPro);
			productVariantValueService.deleteAllPvv(idPro);
			productRepository.deleteById(idPro);
	}
	
	public List<Product> top5HotProduct(){
		Pageable page = PageRequest.of(0, 10);
		return productRepository.top5HotProduct(page);
	}
	public List<Object[]> revenueProduct(){
		return productRepository.revenueProduct();
	}
	
	
	public Page<String[]> fillPublic(int page, int size,String category,String search) {
		Pageable pageable = PageRequest.of(page, size);
		return productRepository.fillPublic(pageable,category,search);
	}
	public List<String[]> finDetailsProduct(Integer id) {
		return productRepository.findDetailsProduct(id);
	}
}

package com.poly.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.models.Cart;
import com.poly.models.CartItem;
import com.poly.models.ProductVariantValue;
import com.poly.repositories.CartItemRepository;
import com.poly.repositories.CartRepository;

@Service
public class CartItemService {
@Autowired
CartItemRepository repository;

@Autowired
CartRepository cartRepository;
public List<CartItem> getAllItemByUsername(String username){
	return repository.findAllItemByUsername(username);
}
	public CartItem saveItem(CartItem cartItem) {
		if (cartItem.getPvv().getId() == null) {
			cartItem.setPvv(null);
		}
		 return repository.save(cartItem);
	}
	public void addCartItem(String username, Integer idPvv,ProductVariantValue pvv, int quantity) {
		CartItem item = repository.findCartItem(username, idPvv);
		if (item != null) {
			item.setQuantity(item.getQuantity()+quantity);
			item.setTotal(item.getQuantity()*pvv.getPrice());
			saveItem(item);
		}else {
			CartItem newItem = new CartItem();
			newItem.setPvv(pvv);
			newItem.setCart(cartRepository.findById(username).get());
			newItem.setQuantity(quantity);
			newItem.setTotal(newItem.getQuantity()*pvv.getPrice());
			saveItem(newItem);
		}
		
	}
	
	public void updateCartItem(String username, Integer idPvv,int quantity) {
		CartItem item = repository.findCartItem(username, idPvv);
		item.setQuantity(quantity);
		item.setTotal(item.getPvv().getPrice()*item.getQuantity());
		saveItem(item);
	}
	public void deleteCartItem(String username,Integer indexCart) {
		List<CartItem> cart = getAllItemByUsername(username);		
		repository.deleteCartItem(username, cart.get(indexCart).getId());
	}
	public void updateCartItemWhenDeletePvv(Integer idPvv) {
			repository.updateCartItemWhenDeleteIdPvv(idPvv);
		
		
	}
	public void updateCartItemWhenDeletePro(Integer idPro) {
		repository.updateCartItemWhenDeleteIdPro(idPro);
	}
	
	
}

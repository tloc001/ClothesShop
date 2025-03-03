package com.poly.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.models.Cart;
import com.poly.repositories.CartRepository;

@Service
public class CartService {
@Autowired
CartRepository repository;
public void saveCart(Cart cart) {
	repository.save(cart);
}

public Cart findCartByUsername(String username) {
	return repository.findById(username).get();
}

}

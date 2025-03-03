package com.poly.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.poly.models.Cart;
import com.poly.models.User;
import com.poly.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CartService cartService;
	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	public Optional<User> findByUsername(String username) {
		return userRepository.findById(username);
	}

	public Boolean register(User entity) {
		Optional<User> user = userRepository.findById(entity.getUsername());
		if (user.isPresent()) {
			return false;
		}
		String password = entity.getPassword();
		entity.setCreatedAt(LocalDateTime.now());
		entity.setPassword(bCryptPasswordEncoder.encode(password));		
		userRepository.save(entity);
		Cart cart = new Cart();
		cart.setUsername(entity.getUsername());	
		cartService.saveCart(cart);
		return true;
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public void UpdateUser(User entity) {
		Optional<User> user = userRepository.findById(entity.getUsername());
		
		if (entity.getPassword().equals(user.get().getPassword())) {
			System.err.println("Mật khẩu không thay đổi");
			userRepository.save(entity);
		}else {
			String password = entity.getPassword();
			entity.setPassword(bCryptPasswordEncoder.encode(password));
			userRepository.save(entity);
		}
		
	}
}

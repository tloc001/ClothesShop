package com.poly.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.poly.models.CartItem;
import com.poly.models.CustomUserDetails;
import com.poly.models.User;
import com.poly.repositories.CartItemRepository;
import com.poly.repositories.CartRepository;

import jakarta.websocket.server.ServerEndpoint;
@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserService userService;
	@Autowired
	private SessionCartService cartService;
	@Autowired
	private CartRepository cartRepository;
	@Autowired CartItemRepository cartItemRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userService.findByUsername(username);
		System.err.println("chạy qua userdetails");
		if (!user.isPresent()) {
			throw new UsernameNotFoundException("Không tìm thấy Username này "+ username);
		}
	
		Collection<GrantedAuthority> grantedAuthoritySet = new HashSet<GrantedAuthority>();
		grantedAuthoritySet.add(new SimpleGrantedAuthority(user.get().getRole()));
		return new CustomUserDetails(user, grantedAuthoritySet);
	}

}

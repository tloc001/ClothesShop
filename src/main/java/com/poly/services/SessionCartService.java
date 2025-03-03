package com.poly.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.poly.models.CartItem;
import com.poly.models.CustomUserDetails;
import com.poly.models.ProductVariantValue;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;

@Service
@SessionScope
public class SessionCartService {
	@Autowired
	ProductVariantValueService productVariantValueService;
	@Autowired
	CartItemService cartItemService;
	@Autowired
	CartService cartService;

	public String checkLogin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()
				&& !(authentication instanceof AnonymousAuthenticationToken)) {

			Object principal = authentication.getPrincipal();

			if (principal instanceof CustomUserDetails) {
				return ((CustomUserDetails) principal).getUsername(); // Lấy username từ UserDetails
			}
		}
		return null; // Chưa đăng nhập
	}
	

	private List<CartItem> cart = new ArrayList<>();

	public void updateCartFromDB() {
		
		if (checkLogin() != null) {
			List<CartItem> cart = cartItemService.getAllItemByUsername(checkLogin());
			for (int i = 0; i < cart.size(); i++) {
				CartItem item = cart.get(i);
				if (item.getPvv().getId() != null) {
					item.setPvv(productVariantValueService.findPvvByIdPvv(item.getPvv().getId()));
					item.setTotal(item.getPvv().getPrice()*item.getQuantity());
				}
				cartItemService.saveItem(item);
			}
		}else {
			for (int i = 0; i < cart.size(); i++) {
				CartItem item = cart.get(i);
				item.setPvv(productVariantValueService.findPvvByIdPvv(item.getPvv().getId()));
				if (item.getPvv().getId() != null) {
					item.setTotal(item.getPvv().getPrice()*item.getQuantity());
				}
				cart.set(i, item);
			}
		}
	}
	public void addItemAfterLogin(Integer idPvv, int quantity) {
		ProductVariantValue pvv = productVariantValueService.findPvvByIdPvv(idPvv);
		if (checkLogin() != null) {
			cartItemService.addCartItem(checkLogin(), idPvv, pvv, quantity);
		}
	}
	
	

	public int addItem(Integer idPvv, int quantity) {
		ProductVariantValue pvv = productVariantValueService.findPvvByIdPvv(idPvv);
		if (checkLogin() != null) {
			cartItemService.addCartItem(checkLogin(), idPvv, pvv, quantity);
		} else {
			if (cart.isEmpty()) {
				CartItem item = new CartItem();
				item.setPvv(pvv);
				item.setQuantity(quantity);
				item.setTotal(pvv.getPrice() * item.getQuantity());
				cart.add(item);
			} else {
				Boolean check = false;
				for (int i = 0; i < cart.size(); i++) {
					if (cart.get(i).getPvv().getId() == idPvv) {
						CartItem item = cart.get(i);
						item.setQuantity(item.getQuantity() + quantity);
						item.setTotal(pvv.getPrice() * item.getQuantity());
						cart.set(i, item);
						check = true;
						break;
					}
				}
				if (check == false) {
					CartItem item = new CartItem();
					item.setPvv(pvv);
					item.setQuantity(quantity);
					item.setTotal(pvv.getPrice() * item.getQuantity());
					cart.add(item);
				}
			}
			
		}
		init();
		logCartContents();
		return pvv.getProduct().getId();
	}

	public void deteleItem(int index) {
		if (checkLogin() != null) {
			cartItemService.deleteCartItem(checkLogin(), index);

		} else {
			cart.remove(index);
		}
	}

	public void updateItem(Integer idPvv, int quantity) {
		if (checkLogin() != null) {
			cartItemService.updateCartItem(checkLogin(), idPvv, quantity);
		} else {
			for (int i = 0; i < cart.size(); i++) {
				if (cart.get(i).getPvv().getId() == idPvv) {
					CartItem item = cart.get(i);
					item.setQuantity(quantity);
					item.setTotal(item.getPvv().getPrice() * item.getQuantity());
					cart.set(i, item);
					break;
				}
			}
		}

	}
	@PostConstruct
	public void init() {
	    System.out.println("SessionCartService initialized, cart size: " + cart.size());
	}
	public void logCartContents() {
	    System.out.println("🛒 Cart contents after login: ");
	    for (CartItem item : cart) {
	        System.out.println(" - ProductVariantValue ID: " + item.getPvv().getId() + ", Quantity: " + item.getQuantity());
	    }
	}



	public int getCount() {
		int quantity = 0;
		if (checkLogin() != null) {
			List<CartItem> cart = cartItemService.getAllItemByUsername(checkLogin());
			
			for (CartItem qty : cart) {
				if (qty.getPvv().getId() != null) {
					quantity += qty.getQuantity();
				}
			}
		} else {
			for (CartItem qty : cart) {
				if (qty.getPvv().getId() != null) {
					quantity += qty.getQuantity();
				}
			}
		}

		return quantity;
	}

	public List<CartItem> getCart() {
		if (checkLogin() != null) {
			
			return cartItemService.getAllItemByUsername(checkLogin());
		}
		return cart;
	}
	public List<CartItem> getCartSession() {	
		return cart;
	}

	public void setCart(List<CartItem> cart) {
		this.cart = cart;
	}

}

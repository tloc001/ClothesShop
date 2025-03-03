package com.poly.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.models.CustomUserDetails;
import com.poly.models.Order;
import com.poly.models.OrderItem;
import com.poly.models.User;
import com.poly.services.CartService;
import com.poly.services.CategoryService;
import com.poly.services.OrderItemService;
import com.poly.services.OrderService;
import com.poly.services.ProductService;
import com.poly.services.ProductVariantValueService;
import com.poly.services.SessionCartService;
import com.poly.services.UserService;
import com.poly.services.VariantService;
import com.poly.services.VariantValueService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/user")
public class UserInfoController {
	@Autowired
	ProductService productService;
	@Autowired
	OrderItemService itemService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	VariantService variantService;
	@Autowired
	ProductVariantValueService productVariantValueService;
	@Autowired
	SessionCartService sessionCartService;
	@Autowired
	VariantValueService variantValueService;
	@Autowired CartService cartService;
	@Autowired OrderService orderService;
	@Autowired UserService userService;
	@ModelAttribute("sessionCart")
	public int getSessionCart() {
	    return sessionCartService.getCount();
	}
	
	
	@ModelAttribute("user")
    public String checkLogin(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userDetails != null ? "Xin chào : " + userDetails.getFullname()+ " !" : "";
    }
	
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
	
	@GetMapping("/my-order")
	public String getMethodName1(Model model) {
		User user = userService.findByUsername(checkLogin()).get();
		List<Order> listOrder = orderService.findAllOrderByUserName(user.getUsername());
		List<OrderItem> listOrderItem = itemService.myOrderItem(user.getUsername());
		model.addAttribute("listOrder",listOrder);
		System.err.println(listOrderItem.size());
		model.addAttribute("listOrderItem",listOrderItem);
		return "my-order";
	}
	@GetMapping("/statistics")
	public String getMethodName2(Model model) {
		User user = userService.findByUsername(checkLogin()).get();
		List<Order> listOrder = orderService.findAllOrderByUserName(user.getUsername());
		List<OrderItem> listOrderItem = itemService.myOrderItem(user.getUsername());
		model.addAttribute("listOrder",listOrder);
		System.err.println(listOrderItem.size());
		model.addAttribute("listOrderItem",listOrderItem);
		return "user-order-statistics";
	}
	
}

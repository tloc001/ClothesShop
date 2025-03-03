package com.poly.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poly.models.CartItem;
import com.poly.models.CustomUserDetails;
import com.poly.models.OrderItem;
import com.poly.models.ProductVariantValue;
import com.poly.models.User;
import com.poly.services.CartItemService;
import com.poly.services.OrderItemService;
import com.poly.services.OrderService;
import com.poly.services.ProductVariantValueService;
import com.poly.services.SessionCartService;
import com.poly.services.UserService;
import com.poly.services.VariantValueService;
import com.poly.utils.SendMailService;

import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/checkout")
public class CheckoutConTroller {
	@Autowired
	UserService userService;
	@Autowired
	SessionCartService sessionCartService;
	@Autowired
	ProductVariantValueService productVariantValueService;
	@Autowired
	VariantValueService variantValueService;
	@Autowired
	CartItemService cartItemService;
	@Autowired
	OrderService orderService;
	@Autowired
	SendMailService mailService;
	@ModelAttribute("sessionCart")
	public int getSessionCart() {
	    return sessionCartService.getCount();
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

	@ModelAttribute("user")
    public String checkLogin(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userDetails != null ? "Xin chào : " + userDetails.getFullname() : "";
    }
	
	@PostMapping("/")
	public String getMethodName(@ModelAttribute("inforUser") User inforUser,Model model,@RequestParam("proSelected") Integer[] selected) {
		 inforUser = userService.findByUsername(checkLogin()).get();
		 List<CartItem> listItem = cartItemService.getAllItemByUsername(checkLogin());		 
		 List<CartItem> listSelected = new ArrayList<>();
		for (int i = 0; i < selected.length; i++) {			
					listSelected.add(listItem.get(selected[i]));				
		}		 
			List<String> values = new ArrayList<>();
			 for (CartItem item : listSelected) {
				 	
						String sku = item.getPvv().getSku();
						String[] array = sku.split("-");
						String temp="";
						for (int i = 0; i < array.length; i++) {
							String vv = variantValueService.value(Integer.parseInt(array[i]));
							if (i+1 != array.length) {
								temp+=vv+"-";
							}else {
								temp+=vv;
							}
						
					}
						values.add(temp);

			}
			model.addAttribute("values",values);
		 model.addAttribute("selected",listSelected);
		 model.addAttribute("inforUser",inforUser);
		return "checkout";
	}
	public void sendMailForCustomer(String email,String fullname,List<OrderItem> listItems,List<String> values ) {
	    mailService.sendCustomEmail(email,fullname, listItems,values);
	}
	@PostMapping("/order")
	public String postMethodName(@RequestParam("idCartItem") Integer[] idCartItem, @RequestParam("idPvv") Integer[] idPvvs,
			@RequestParam("qtyItem") Integer[] qtyItems,	@RequestParam("notes") String notes
			,Model model,@ModelAttribute("inforUser") User infor) {
		User user = userService.findByUsername(checkLogin()).get();
		infor.setUsername(checkLogin());
		infor.setCreatedAt(user.getCreatedAt());
		infor.setEnabled(user.getEnabled());
		infor.setPassword(user.getPassword());
		infor.setRole(user.getRole());
		List<OrderItem> orderItems = new ArrayList<>();
		List<String> values = new ArrayList<>();
		for (int i = 0; i < idPvvs.length; i++) {
			OrderItem item = new OrderItem();
			item.setPvv(productVariantValueService.findPvvByIdPvv(idPvvs[i]));
			
			String[] temp = item.getPvv().getSku().split("-");
			String a = "";
			for (String idvv : temp) {
				a += variantValueService.value(Integer.parseInt(idvv)) + " ";
			}
			values.add(a);
			item.setQuantity(qtyItems[i]);
			item.setPriceAtOrder(item.getPvv().getPrice());
			item.setTotal(qtyItems[i]* item.getPriceAtOrder());
			orderItems.add(item);
		}
		

		sendMailForCustomer(infor.getEmail().trim(),user.getFullname(),orderItems, values);
		orderService.createOrder(infor, orderItems, notes, idCartItem);
		return "redirect:/public/success-order";


	}
	
	
	
	
}

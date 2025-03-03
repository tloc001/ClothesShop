package com.poly.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.models.CartItem;
import com.poly.models.CustomUserDetails;
import com.poly.models.ProductDemo;
import com.poly.models.ProductVariantValue;
import com.poly.models.VariantValue;
import com.poly.services.CartService;
import com.poly.services.CategoryService;
import com.poly.services.ProductService;
import com.poly.services.ProductVariantValueService;
import com.poly.services.SessionCartService;
import com.poly.services.VariantService;
import com.poly.services.VariantValueService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;


@Controller
@RequestMapping("/public")
public class ShopController {
	@Autowired
	ProductService productService;
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
	@ModelAttribute("sessionCart")
	public int getSessionCart() {
	    return sessionCartService.getCount();
	}
	
	
	@ModelAttribute("user")
    public String checkLogin(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userDetails != null ? "Xin chào : " + userDetails.getFullname()+ " !" : "";
    }
	
	@GetMapping("/shop")
	public String form(@RequestParam(value = "page",required = false,defaultValue = "0") Integer page,
			@RequestParam(value="priceRange",required = false,defaultValue = "") Float priceRange,
			@RequestParam(value = "search",defaultValue = "") String search
			,@RequestParam(value ="categoryName",defaultValue = "") String category,
			Model model) {
		model.addAttribute("category",category);
		model.addAttribute("search",search);
            model.addAttribute("ssPage", page); // Lần đầu truy cập sẽ gán giá trị 0
			model.addAttribute("listP",productService.fillPublic((int)model.getAttribute("ssPage"),9,category,search));
			model.addAttribute("listCategory",categoryService.categoryExist());
		int totalPage = (int) Math.ceil(productService.findAllProduct().size()/9.0);
		model.addAttribute("totalPage",totalPage);
		return "shop";
	}
	
	@GetMapping("/shop/product-details/{id}")
	public String getMethodName(Model model,@PathVariable("id") Integer id ) {
		
		model.addAttribute("details",productVariantValueService.findPvvByIdProduct(id));
		model.addAttribute("infor",productService.finDetailsProduct(id));
		List<ProductVariantValue> attrs = productVariantValueService.findPvvByIdProduct(id);
		List<String> values = new ArrayList<>();
		for (int i = 0; i < attrs.size(); i++) {
			String[] temp = attrs.get(i).getSku().split("-");
			String a = "";
			for (String idvv : temp) {
				a += variantValueService.value(Integer.parseInt(idvv)) + " ";
			}
			values.add(a);
		}
		model.addAttribute("values",values);
		return "product-details";
	}
	@GetMapping("/addCart/{id}")
	
	public String postMethodName(@RequestParam("quantity") int qty,@PathVariable("id") Integer idPvv) {
		int a = sessionCartService.addItem(idPvv, qty);
	 return "redirect:/public/shop/product-details/"+ a;
		
	
	}
	
	@GetMapping("/shop-cart")
	public String getMethodName(Model model) {
		sessionCartService.updateCartFromDB();
		model.addAttribute("cart", sessionCartService.getCart());
		
		List<String> values = new ArrayList<>();
		 for (CartItem item : sessionCartService.getCart()) {
			 	if (item.getPvv().getSku() == null) {
					values.add("");
				}else{
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
				
		}
		model.addAttribute("values",values);
		return "shop-cart";
	}
	@PostMapping("/update-cart")
	public String getMethodName(@RequestParam("quantity") Integer[] quantity) {
		List<CartItem> cart = sessionCartService.getCart();
		for (int i = 0; i < cart.size(); i++) {
				 if (cart.get(i).getPvv().getId() != null) {
						sessionCartService.updateItem(cart.get(i).getPvv().getId(), quantity[i]);
				}
		}
		return "redirect:/public/shop-cart";
	}
	@PostMapping("/delete-cart/{id}")
	public String postMethodName(@PathVariable("id") int index) {
		sessionCartService.deteleItem(index);
		return "redirect:/public/shop-cart";
	}
	@GetMapping("/success-order")
	public String getMethodName() {
		return "success-order";
	}
	
	
	
	
	

}

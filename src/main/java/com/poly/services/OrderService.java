package com.poly.services;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.models.Cart;
import com.poly.models.Order;
import com.poly.models.OrderItem;
import com.poly.models.Product;
import com.poly.models.ProductVariantValue;
import com.poly.models.User;
import com.poly.repositories.CartItemRepository;
import com.poly.repositories.OrderItemRepository;
import com.poly.repositories.OrderRepository;
import com.poly.repositories.ProductRepository;
import com.poly.repositories.ProductVariantValueRepository;
import com.poly.repositories.UserRepository;

@Service
public class OrderService {
	@Autowired
	OrderRepository orderRepository;

	@Autowired 
	UserRepository userRepository;
	@Autowired
	OrderItemRepository itemRepository;
	@Autowired
	CartItemRepository cartItemRepository;
	@Autowired
	ProductVariantValueRepository productVariantValueRepository;
	@Autowired
	ProductRepository productRepository;
	public String createOrder (User user,List<OrderItem> listItem,String notes, Integer[] idCartItem ) {
		user = userRepository.save(user);
		Order create = new Order();
		create.setAddress(user.getAddress());
		create.setUser(user);
		create.setNotes(notes);
		Float sum= 0f;
		for (OrderItem orderItem : listItem) {
			sum += orderItem.getTotal();
		}
		create.setSubTotal(sum);
		create.setStatus("pending");
		create = orderRepository.save(create);
		
		for (OrderItem orderItem : listItem) {
			orderItem.setOrder(create);
			itemRepository.save(orderItem);
		}
		
		for (int i = 0; i < idCartItem.length; i++) {	
			ProductVariantValue pvv = productVariantValueRepository.findById(listItem.get(i).getPvv().getId()).get();
			pvv.setTurnbuy(pvv.getTurnbuy() + listItem.get(i).getQuantity());
			pvv.setStock(pvv.getStock()-listItem.get(i).getQuantity());
			productVariantValueRepository.save(pvv);
			cartItemRepository.deleteById(idCartItem[i]);
		}
		List<Integer[]> turnBuyOfProducts = productVariantValueRepository.findTurnBuyByProductId();
		for (int i = 0; i < turnBuyOfProducts.size(); i++) {
			Product product = productRepository.findById(turnBuyOfProducts.get(i)[0]).get();
			product.setTurnbuy(turnBuyOfProducts.get(i)[1]);
			productRepository.save(product);
		}
		
		return "okk";
	}
	public List<Order> findAllOrderByUserName(String username) {
		return orderRepository.findOrderByUsername(username);
	}
	public List<Object[]> sumTotalBillByUser(){
		return orderRepository.sumTotalBillByUser();
	}
	
}

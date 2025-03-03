package com.poly.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.models.OrderItem;
import com.poly.repositories.OrderItemRepository;

@Service
public class OrderItemService {
@Autowired
OrderItemRepository itemRepository;
public void createOrderItem(OrderItem item ) {
	itemRepository.save(item);
}
public List<OrderItem> myOrderItem(String username){
	return itemRepository.findOrderItemByUserName(username);	
}
}

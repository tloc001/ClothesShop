package com.poly.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poly.models.Order;
import com.poly.models.OrderItem;
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
	@Query("FROM OrderItem oi WHERE oi.order.id = ?1")
	List<OrderItem> findOrderItem(Integer idOrder);
	@Query("\r\n"
			+ "  SELECT oi \r\n"
			+ "  FROM OrderItem oi inner join Order o on o.id = oi.order.id where o.user.username = ?1 ")
	List<OrderItem> findOrderItemByUserName(String username);
	
	
}

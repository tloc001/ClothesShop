package com.poly.repositories;

import java.util.List;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poly.models.Order;
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	@Query("FROM Order o WHERE user.username = ?1 ORDER BY o.orderDate ASC")
	List<Order> findOrderByUsername(String username);
	@Query("SELECT o.user.username, SUM(o.subTotal) FROM Order o GROUP BY o.user.username ORDER BY SUM(o.subTotal) ASC")
	List<Object[]> sumTotalBillByUser();
}

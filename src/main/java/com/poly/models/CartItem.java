package com.poly.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="cart_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "product_variant_id", nullable = true)
	private ProductVariantValue pvv;
	private Integer quantity;
	private Float total;
	@ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "username", nullable = false) // Trỏ đến `username` của `Cart`
    private Cart cart;
	@PostLoad
    private void init() {
        if (this.pvv == null) {
            this.pvv = new ProductVariantValue(); // Tạo đối tượng rỗng nếu null
        }
	}
}

package com.poly.models;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_variant_values")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantValue {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY  )
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name ="product_id")
	private Product product;
	
	private Float price;
	@Column(name="old_price")
	private Float oldprice;
	private Integer stock;
	private String sku;
	private String image;
	private Integer turnbuy;
	


}

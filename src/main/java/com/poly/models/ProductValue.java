package com.poly.models;

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
@Table(name = "productvalues")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductValue {
	@Id
	@Column(name = "value_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer valueId;
	
	private String value;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	@ManyToOne
	@JoinColumn(name = "attribute_id")
	private Attribute attribute;

}

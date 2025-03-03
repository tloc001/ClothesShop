package com.poly.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDemo {
	private Integer id;
	private String name;
	private Double price;
	private String photo;
}

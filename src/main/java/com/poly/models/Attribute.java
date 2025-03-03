package com.poly.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="Attributes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attribute {
	@Id
	@Column(name = "attribute_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer attributeId;
	private String name;
//	@OneToMany(mappedBy = "attribute", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ProductValue> productValues = new ArrayList<>();
//
//    // Add convenience methods for adding and removing product values
//    public void addProductValue(ProductValue productValue) {
//        productValues.add(productValue);
//        productValue.setAttribute(this);
//    }
//
//    public void removeProductValue(ProductValue productValue) {
//        productValues.remove(productValue);
//        productValue.setAttribute(null);
//    }
}

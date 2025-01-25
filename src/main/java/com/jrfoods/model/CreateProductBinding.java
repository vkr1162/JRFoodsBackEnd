package com.jrfoods.model;

import java.util.HashSet;
import java.util.Set;

import com.jrfoods.entity.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateProductBinding {

	private String title;
	
	private String description;
	
	private Integer price;
	
	private Integer discountedPrice;
	
	private Integer discountPercentage;
	
	private Integer quantity;
	
	private String brand;
	
	private Set<Size> sizes = new HashSet<>();
	
	private String imageUrl;
	
	private String topLevelCategory;
	
	private String bottomLevelCategory;
}

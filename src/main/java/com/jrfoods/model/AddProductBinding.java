package com.jrfoods.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddProductBinding {
	
	private Integer productId;
	
	private String size;
	
	private Integer quantiy;
	
	private Integer price;

}
package com.jrfoods.model;

import com.jrfoods.entity.UserDtls;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddressBinding {

	private String firstName;
	
	private String lastName;
	
	private String streetAddress;
	
	private String city;
	
	private String state;
	
	private String zipCode;
	
	private UserDtls userDtls;
	
	private String mobile;

}

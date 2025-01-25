package com.jrfoods.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentInfo {

	private String cardHolderName;
	
	private String cardName;
	
	private LocalDate expDate;
	
	private String cvv;
}

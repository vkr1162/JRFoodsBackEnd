package com.jrfoods.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDtls {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_id")
	private Integer userId;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String password;
	
	private String role;
	
	private String mobileNo;
	
	@OneToMany(mappedBy="userDtls", cascade = CascadeType.ALL)
	private List<Address> userAddress = new ArrayList<>();
	
	@Embedded
	@ElementCollection
	@CollectionTable(name="payment_info", joinColumns = @JoinColumn(name="user_id"))
	private List<PaymentInfo> paymentInfo = new ArrayList<>();
	
	@OneToMany(mappedBy = "userDtls", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Rating> ratings = new ArrayList<>();
	
	@OneToMany(mappedBy="userDtls", cascade=CascadeType.ALL)
	@JsonIgnore
	private List<Review> reviews = new ArrayList<>();
	
	private LocalDateTime createdAt;
	
	
}

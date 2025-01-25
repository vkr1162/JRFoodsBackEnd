package com.jrfoods.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jrfoods.entity.Cart;
import com.jrfoods.entity.UserDtls;
import com.jrfoods.exception.UserException;
import com.jrfoods.model.LoginRequest;
import com.jrfoods.repository.UserRepository;
import com.jrfoods.response.AuthResponse;


@Service
public class UserServiceImpl implements UserDetailsService, UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder pwdEncoder;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserDtls userDtls = userRepo.findByEmail(username);
		if(userDtls==null) {
			throw new UsernameNotFoundException("User not found with email");
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		
		return new User(userDtls.getEmail(),userDtls.getPassword(), authorities);
	}

	@Override
	public UserDtls findUserById(Integer userId) throws UserException {
		
		Optional<UserDtls> opt = userRepo.findById(userId);
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new UserException("User not Found with Id" + userId);
	}

	@Override
	public UserDtls findUserByJwt(String jwt) throws UserException {
		String email = jwtProvider.getEmailFromToken(jwt);
		UserDtls userDtls = userRepo.findByEmail(email);
		if(userDtls==null) {
			throw new UserException("User Not Found with email" + email);
		}
		return userDtls;
	}

	@Override
	public UserDtls createUser(UserDtls userDtls) throws UserException {
		String email = userDtls.getEmail();
		String pwd = userDtls.getPassword();
		String firstName = userDtls.getFirstName();
		String lastName = userDtls.getLastName();
		
		UserDtls isEmailExist = userRepo.findByEmail(email);
		
		if(isEmailExist !=null) {
			throw new UserException("Email is Already Registered with another account");
		}
		
		UserDtls createdUser = new UserDtls();
		
		createdUser.setEmail(email);
		createdUser.setPassword(pwdEncoder.encode(pwd));
		createdUser.setFirstName(firstName);
		createdUser.setLastName(lastName);
		createdUser.setCreatedAt(LocalDateTime.now());
		UserDtls userSaved =  userRepo.save(createdUser);
		return userSaved;
	}

	@Override
	public AuthResponse loginUser(LoginRequest loginRequest) throws UserException {
		
		String email = loginRequest.getEmail();
		String pwd = loginRequest.getPassword();
		Authentication authentication = authenticate(email,pwd);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvider.generateToken(authentication);
		
		AuthResponse authResponse = new AuthResponse(token, "Login Success");
		return authResponse;
	}

	private Authentication authenticate(String email, String pwd) {
		
		UserDetails userDetails = loadUserByUsername(email);
		if(userDetails==null) {
			throw new BadCredentialsException("Invalid UserName");
		}
		if(!pwdEncoder.matches(pwd, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid Password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

	

}

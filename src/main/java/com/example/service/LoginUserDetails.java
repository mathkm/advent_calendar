package com.example.service;

import com.example.domain.User;
import lombok.Data;
import org.springframework.security.core.authority.AuthorityUtils;

@Data
public class LoginUserDetails extends org.springframework.security.core.userdetails.User {
	private final User user;
	
	public LoginUserDetails(User user){
		super(user.getUsername(),user.getPassword(),AuthorityUtils.createAuthorityList("role"));
		this.user=user;
	}
}

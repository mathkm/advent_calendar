package com.example.web;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserForm {
	
	@NotNull
	@Size(min = 1,max = 255)
	private String username;
	
	@NotNull
	@Size(min = 4,max = 255)
	private String password;
	
	@NotNull
	@Size(min = 1,max = 255)
	private String email;
	
	@NotNull
	@Size(min = 1,max = 127)
	private Integer role;
}

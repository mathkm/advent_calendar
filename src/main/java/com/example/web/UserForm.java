package com.example.web;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class UserForm {

	@NotBlank(message = "空欄です。")
	@Size(max = 255, message = "文字数は255文字以下です。")
	private String username;

	@NotBlank(message = "空欄です。")
	@Size(max = 255, message = "文字数は255文字以下です。")
	private String password;

	@NotBlank(message = "空欄です。")
	@Size(max = 255, message = "文字数は255文字以下です。")
	private String email;

	@NotNull
	private byte role;

}

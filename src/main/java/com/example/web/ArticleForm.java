package com.example.web;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class ArticleForm {

	@NotNull
	private java.sql.Date calendarDate;
	@NotNull
	private Integer userid;
	@NotBlank(message = "空欄です。")
	@Size(min = 1, max = 255, message = "文字数は255文字以下です")
	private String title;
	@Size(min = 1, max = 255, message = "文字数は255文字以下です")
	private String url;
}
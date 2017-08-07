package com.example.web;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ArticleForm {

	@NotNull
	private java.sql.Date calendarDate;
	@NotNull
	private Integer userid;
	@NotNull
	@Size(min = 1,max = 255)
	private String title;
	@NotNull
	private String url;
}
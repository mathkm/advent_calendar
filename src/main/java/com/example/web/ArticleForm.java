package com.example.web;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ArticleForm {

	@NotNull
	@Size(min = 1, max = 255)
	private String title;

	@NotNull
	@Size(min = 6, max = 255)
	private String url;
}
package com.example.web;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class ThemeForm {

	@NotBlank(message = "タイトルを記入してください。")
	@Size(max = 255, message = "タイトルは255文字以下で記入してください。")

	private String name;

	@NotBlank(message = "詳細を記入してください。")
	@Size(max = 255, message = "詳細は255文字以下で記入してください。")
	private String detail;

	@NotNull
	private java.sql.Date calendarMonth;

	@NotNull
	private String enabledDates;

	private int createdUserid;

	private int updatedUserid;

}

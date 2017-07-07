package com.example.domain;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "articles")
public class Article {
	@Id
	@GeneratedValue
	@Column(nullable = false)
	private Integer id;
	@Column(nullable = false, name = "calendar_date")
	private Calendar calendarDate;
	@Column(nullable = false, name = "user_id")
	private Integer userid;
	@Column(nullable = false, name = "title", length = 512, columnDefinition = "TEXT")
	private String[] title;
	@Column(nullable = false, name = "url", length = 512, columnDefinition = "TEXT")
	private String[] url;
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;
}

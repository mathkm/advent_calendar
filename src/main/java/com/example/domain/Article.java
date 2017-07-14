package com.example.domain;

import java.io.Serializable;
import java.sql.Timestamp;
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
public class Article implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(nullable = false, name = "id")
	private Integer id;
	@Column(nullable = false, name = "calendar_date")
	private java.sql.Date calendarDate;
	@Column(nullable = false, name = "user_id")
	private Integer userid;
	@Column(nullable = false, name = "title", length = 512, columnDefinition = "TEXT")
	private String title;
	@Column(nullable = false, name = "url", length = 512, columnDefinition = "TEXT")
	private String url;
	@Column(nullable = false, name = "created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	@Column(nullable = false, name = "updated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;
}

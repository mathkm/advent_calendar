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
@Table(name = "themes")
public class Theme implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(nullable = false)
	private Integer id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false, name = "detail", length = 512, columnDefinition = "TEXT")
	private String detail;
	@Column(nullable = false, name = "calendar_month")
	private java.sql.Date calendarMonth;
	@Column(nullable = false, name = "enabled_dates", length = 512, columnDefinition = "TEXT")
	private String enabledDates;
	@Column(nullable = false, name = "created_user_id")
	private Integer createdUserid;
	@Column(nullable = false, name = "created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	@Column(nullable = false, name = "updated_user_id")
	private Integer updatedUserid;
	@Column(nullable = false, name = "updated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;
}
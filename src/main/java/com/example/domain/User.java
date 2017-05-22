package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue
	@Column(nullable=false)
	private Integer id;
	@Column(nullable=false)
	private String username;
	@JsonIgnore
	@Column(nullable=false)
	private String password;
	@Column(nullable=false)
	private String email;
	@Column(nullable=false)
	private byte role;
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;
}

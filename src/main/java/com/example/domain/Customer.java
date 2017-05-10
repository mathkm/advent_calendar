package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
	@Id
	@GeneratedValue
	private Integer id;
	private String firstName;
	private String lastName;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = true, name="username")
	private User user;
}

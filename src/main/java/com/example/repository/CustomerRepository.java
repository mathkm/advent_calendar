package com.example.repository;

import com.example.domain.Customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
	@Query("SELECT x FROM customers x ORDER BY x.firstname , x.lastname")
	List<Customer> findAllOrderById();
//	Page<Customer> findAllOrderByName(Pageable pageable);
}

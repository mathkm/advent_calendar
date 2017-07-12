package com.example.repository;

import com.example.domain.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public interface UserManageRepository extends JpaRepository<User,Integer> {
	@Query("SELECT x FROM User x ORDER BY x.id")
	List<User> findAllOrderById();
	public User findByUsername(String username);
}

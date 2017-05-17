package com.example.repository;

import com.example.domain.UserManage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserManageRepository extends JpaRepository<UserManage,Integer> {
	@Query("SELECT x FROM users x ORDER BY x.id")
	List<UserManage> findAllOrderById();
}

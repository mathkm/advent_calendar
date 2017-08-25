package com.example.repository;

import com.example.domain.User;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserManageRepository extends JpaRepository<User, Integer> {
	@Query("SELECT x FROM User x ORDER BY x.created ASC")
	List<User> findAllOrderByCreated();

	public User findByUsername(String username);

	@Query("UPDATE User x SET x.username = :username , x.email = :email,x.role = :role WHERE x.id = :id")
	@Modifying
	int updateById(@Param("username") String username, @Param("email") String email, @Param("role") byte role,
			@Param("id") int id);
}

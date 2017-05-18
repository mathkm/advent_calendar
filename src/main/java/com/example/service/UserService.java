package com.example.service;

import com.example.domain.User;
import com.example.repository.UserManageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
	@Autowired
	UserManageRepository userRepository;
	
	public List<User> findAll(){
		return userRepository.findAllOrderById();
	}
	
	public User findOne(Integer id){
		return userRepository.findOne(id);
	}
	
	public User create(User user){
		return userRepository.save(user);
	}
	
	public User update(User user){
		return userRepository.save(user);
	}
	
	public void delete (Integer id){
		userRepository.delete(id);
	}

}

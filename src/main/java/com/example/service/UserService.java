package com.example.service;

import com.example.domain.UserManage;
import com.example.repository.UserManageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
	@Autowired
	UserManageRepository userManageRepository;
	
	public List<UserManage> findAll(){
		return userManageRepository.findAllOrderById();
	}
	
	public UserManage findOne(Integer id){
		return userManageRepository.findOne(id);
	}
	
	public UserManage create(UserManage user){
		return userManageRepository.save(user);
	}
	
	public UserManage update(UserManage user){
		return userManageRepository.save(user);
	}
	
	public void delete (Integer id){
		userManageRepository.delete(id);
	}

}

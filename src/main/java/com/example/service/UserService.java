package com.example.service;

import com.example.domain.User;
import com.example.pw.GenPassword;
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

	public List<User> findAll() {
		return userRepository.findAllOrderByCreated();
	}

	public User findOne(Integer id) {
		return userRepository.findOne(id);
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public User create(User user) {
		// 入力されたパスワードをハッシュ化してから登録する。
		String beforePw = user.getPassword();
		GenPassword genPw = new GenPassword();
		String afterpw = genPw.hashpw(beforePw);
		user.setPassword(afterpw);
		return userRepository.save(user);
	}

	public int update(String username, String email, byte role, int id) {
		return userRepository.updateById(username, email, role, id);
	}

	public void delete(Integer id) {
		userRepository.delete(id);
	}

}

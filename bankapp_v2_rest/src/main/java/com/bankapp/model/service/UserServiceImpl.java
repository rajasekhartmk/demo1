package com.bankapp.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankapp.model.entities.User;
import com.bankapp.model.repo.UserRepository;
@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void addUser(User user) {
		userRepository.save(user);
	}
	@Override
	public List<User> findAll() {
		return null;
	}

	@Override
	public void blockUser(Long userId) {
		
	}

	@Override
	public void deleteUser(Long userId) {
	}

}

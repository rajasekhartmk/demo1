package com.custApp.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.custApp.model.persistance.User;
import com.custApp.model.persistance.UserDAO;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDAO userdao;
	
	@Override
	public User getUser(String email, String password) {
		return userdao.getUser(email, password);
	}

	@Override
	public void addUser(User user) {
		userdao.addUser(user);
	}

	@Override
	public List<User> getAllUser() {
		return userdao.getAllUser();
	}

}

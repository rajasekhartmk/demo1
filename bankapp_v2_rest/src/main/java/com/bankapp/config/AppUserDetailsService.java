package com.bankapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bankapp.model.entities.User;
import com.bankapp.model.service.UserService;

@Service
public class AppUserDetailsService implements UserDetailsService{

	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user=userService.findByEmail(email);
		System.out.println("---------------------------");
		System.out.println(user);
		System.out.println("---------------------------");
		if(user==null)
			throw new UsernameNotFoundException("username is not found");
		
		return new org.springframework.security.core.userdetails.User
				(user.getEmail(), user.getPassword(), 
						AuthorityUtils.createAuthorityList(user.getRoles()));
	}

}










package com.bankapp.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {

	@GetMapping(path="/home")
	public String hello(){
		return "hello home";
	}
	
	@GetMapping(path="/clerk")
	public String helloClerk(){
		return "hello to clerk";
	}
	
	@GetMapping(path="/mgr")
	public String helloMgr(){
		return "hello to mgr";
	}
	
	@GetMapping(path="/admin")
	public String helloAdmin(){
		return "hello to admin";
	}
	
}

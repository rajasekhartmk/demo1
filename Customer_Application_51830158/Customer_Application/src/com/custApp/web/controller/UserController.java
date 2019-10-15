package com.custApp.web.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.custApp.model.persistance.User;
import com.custApp.model.persistance.UserNotFoundException;
import com.custApp.model.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	User user = null;

//	@PostConstruct
//	public void init(){
//		userService.addUser(new User("raj", "raj@gmail.com", "abcd", "admin", true));
//		userService.addUser(new User("king", "king@gmail.com", "asdf", "manager", true));
//		userService.addUser(new User("int", "int@gmail.com", "zxcv", "user", true));
//	}
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "redirect:login";
	}
	
	@RequestMapping(value = "login")
	public String loginGet(HttpServletRequest req,ModelMap map) {
		map.addAttribute("userbean", new UserFormBean());
		map.addAttribute("error",req.getParameter("error"));
		return "login";
	}
	
	@RequestMapping(value = "logout")
	public String logout(ModelMap map) {
		map.addAttribute("userbean", new UserFormBean());
		map.addAttribute("message", "you are logged out successfully");
		return "login";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String loginPost(HttpServletRequest req,@Valid @ModelAttribute(value="userbean") UserFormBean userBean, BindingResult bindingResult) {
		if(bindingResult.hasErrors()){
			return "login";
		}else{
			try{
				user=userService.getUser(userBean.getEmail(),userBean.getPassword());
			}catch(UserNotFoundException e){
				return "redirect:login?error=login interrupted";
			}
			HttpSession httpSession=req.getSession();
			httpSession.setAttribute("user", user);
		}
		return "redirect:allcustomer";
		
	}
}

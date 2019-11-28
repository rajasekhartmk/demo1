package com.bankapp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankapp.model.entities.Account;
import com.bankapp.model.entities.Customer;
import com.bankapp.model.repo.AccountRepository;
import com.bankapp.web.controller.bean.AccountRequest;
import com.bankapp.model.service.AccountService;

@RestController
@RequestMapping(path="/api/admin")
public class AccountController {
	
	@Autowired 
	private AccountService accountService;
	
	@PostMapping(path="/account")
	public ResponseEntity<Account> addAccount(@RequestBody AccountRequest request){
		Customer customer=new Customer(request.getName(),
				request.getEmail(), request.getPhone(), request.getAddress(), 
				request.getCity(), request.getCountry());
		Account account=new Account(request.getBalance(), customer, false);
		accountService.createAccount(account);
		
		return  ResponseEntity.ok().body(account);
	}
	

}

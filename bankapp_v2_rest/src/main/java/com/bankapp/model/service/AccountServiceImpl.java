package com.bankapp.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankapp.model.entities.Account;
import com.bankapp.model.entities.AccountTransaction;
import com.bankapp.model.entities.TransactionLog;
import com.bankapp.model.repo.AccountRepository;
import com.bankapp.model.repo.AccountTransactionRepository;
import com.bankapp.model.repo.CustomerRepository;
import com.bankapp.model.repo.TransactionLogRepository;
import com.bankapp.model.service.exceptions.AccountNotFoundException;
import com.bankapp.model.service.exceptions.NotSufficientFundException;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountTransactionRepository accountTransactionRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private TransactionLogRepository transactionLogRepository;

	@Override
	public void blockAccount(Long accountNumber) {

	}

	@Override
	public void createAccount(Account account) {
		accountRepository.save(account);
		customerRepository.save(account.getCustomer());
	}

	@Override
	public void deposit(Long accountNumber, double amount) {
		Account account =findByAccountId(accountNumber) ;
		account.setBalance(account.getBalance() + amount);
		AccountTransaction accountTransaction=new AccountTransaction("deposit", amount);
		account.addAccountTransaction(accountTransaction);
		
		accountRepository.save(account);
		TransactionLog log=new TransactionLog
				(accountNumber, null, "depoist", amount, "gunika", "done");
		transactionLogRepository.save(log);
		//accountTransactionRepository.save(accountTransaction);
	}

	@Override
	public void withdraw(Long accountNumber, double amount) {
		Account account =findByAccountId(accountNumber) ;
		// min bal should be 1000 in any case
		if (account.getBalance() - amount < 1000)
			throw new NotSufficientFundException();
		account.setBalance(account.getBalance() - amount);
		accountRepository.save(account);
	}

	@Override
	public void transfer(Long fromAccNumber, Long toAccNumber, double amount) {
		Account fromAccount=findByAccountId(fromAccNumber);
		Account toAccount=findByAccountId(toAccNumber);
		if (fromAccount.getBalance() - amount < 1000)
			throw new NotSufficientFundException();
		
		fromAccount.setBalance(fromAccount.getBalance() - amount);
		toAccount.setBalance(toAccount.getBalance()+amount);
		
		AccountTransaction accountTransactionToAccount=new AccountTransaction("deposit", amount);
		toAccount.addAccountTransaction(accountTransactionToAccount);
		
		AccountTransaction accountTransactionFromAccount=new AccountTransaction("withdraw", amount);
		fromAccount.addAccountTransaction(accountTransactionFromAccount);

		accountRepository.save(fromAccount);
		accountRepository.save(toAccount);
		
		TransactionLog log=new TransactionLog
				(fromAccNumber, toAccNumber, "transfer", amount, "gunika", "done");
		transactionLogRepository.save(log);
		
	}

	@Override
	public Account findByAccountId(Long accountNumber) {
		return accountRepository.findById(accountNumber)
				.orElseThrow(AccountNotFoundException::new);
	}

}





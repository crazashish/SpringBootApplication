package com.springbootapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootapplication.model.Account;
import com.springbootapplication.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Override
	public Account findByUsername(String username) {
		return accountRepository.findByUsername(username);
	}

}

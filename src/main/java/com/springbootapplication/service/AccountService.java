package com.springbootapplication.service;

import com.springbootapplication.model.Account;

public interface AccountService {
	
	Account findByUsername(String username);

}

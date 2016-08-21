package com.springbootapplication.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springbootapplication.model.Account;
import com.springbootapplication.model.Role;
import com.springbootapplication.service.AccountService;

@Service
public class AccountUserDetailsService implements UserDetailsService {

	@Autowired
	private AccountService accountService;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Account account = accountService.findByUsername(username);
		if (null == account) {
			return null;
		}

		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Role role : account.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getCode()));
		}
		User user = new User(account.getUsername(), account.getPassword(),
				account.isEnabled(), account.isExpired(),
				account.isCredentialexpired(), account.isLocked(), authorities);
		return user;

	}

}

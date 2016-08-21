package com.springbootapplication.service;

import java.util.Collection;

import com.springbootapplication.model.Greeting;

public interface GreetingService {
	
	Collection<Greeting> findAll();
	
	Greeting findOne(Long Id);
	
	Greeting createGreeting(Greeting greeting);
	
	Greeting updateGreeting(Greeting greeting);
	
	void deleteGreeting(Long id);
	
	void evictCache();

}

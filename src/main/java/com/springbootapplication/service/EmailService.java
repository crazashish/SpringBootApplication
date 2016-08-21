package com.springbootapplication.service;

import java.util.concurrent.Future;

import com.springbootapplication.model.Greeting;

public interface EmailService {
	
	Boolean send(Greeting greeting);
	
	void sendAsync(Greeting greeting);
	
	Future<Boolean> sendAyncWithResult(Greeting greeting);

}

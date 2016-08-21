package com.springbootapplication.health;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.springbootapplication.model.Greeting;
import com.springbootapplication.service.GreetingService;

@Component
public class GreetingHealthIndicator implements HealthIndicator {
	
	@Autowired
	private GreetingService greetingService;

	@Override
	public Health health() {
		Collection<Greeting> coll=greetingService.findAll();
		if(null== coll || coll.size()==0){
			return Health.down().withDetail("count", 0).build();
		}
		return Health.up().withDetail("count", coll.size()).build();
	}

}

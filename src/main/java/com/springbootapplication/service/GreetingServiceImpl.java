/**
 * 
 */
package com.springbootapplication.service;

import java.util.Collection;

import javax.management.monitor.CounterMonitor;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.springbootapplication.model.Greeting;
import com.springbootapplication.repository.GreetingRepository;

/**
 * @author Ashu
 *
 */
@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class GreetingServiceImpl implements GreetingService {

/*	private static Map<Long, Greeting> greetingsMap;
	private static BigInteger nextId;*/
	@Autowired
	private GreetingRepository greetingRepository;
	//used with actuator metrics
	@Autowired
	private CounterService counterService;

	@Override
	public Collection<Greeting> findAll() {
		counterService.increment("From findAll");
		Collection<Greeting> greetings = greetingRepository.findAll();
		return greetings;
	}

	@Override
	//responses from the findone method are stored in cache name
	//greetings and indexed by their id
	@Cacheable(value="greetings",key="#id")
	public Greeting findOne(Long id) {
		counterService.increment("From findOne");
		//Greeting greeting = greetingsMap.get(id);
		Greeting greeting = greetingRepository.findOne(id);
		return greeting;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	@CachePut(value="greetings",key="#result.id")
	public Greeting createGreeting(Greeting greeting) {
		counterService.increment("From CreateGreeting");
		if(greeting.getId()!=null){
		     throw new EntityExistsException(
	                    "The id attribute must be null to persist a new entity.");
		}
		//Greeting greetings = save(greeting);
		Greeting greetings = greetingRepository.save(greeting);
		return greetings;
	}
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	@CachePut(value="greetings",key="#greeting.id")
	public Greeting updateGreeting(Greeting greeting) {
		counterService.increment("From UpdateGreeting");
		//Greeting updatedGreeting = save(greeting);
		Greeting findGreeting=findOne(greeting.getId());
		//if there is no entity with given id in database then we cannot update
		if(findGreeting==null){
			throw new NoResultException("Attempt to update a greeting but the entity does not exist");
		}
		Greeting updatedGreeting = greetingRepository.save(greeting);
		return updatedGreeting;
	}

	@Override
	@CacheEvict(value="greetings",key="#id")
	public void deleteGreeting(Long id) {
		//return delete(id);
		greetingRepository.delete(id);
	}

	@Override
	@CacheEvict(value="greetings",allEntries=true)
	public void evictCache() {
		// TODO Auto-generated method stub
		
	}

}

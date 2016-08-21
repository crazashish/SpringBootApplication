/**
 * Rest Controller
 */
package com.springbootapplication.controller;

import java.math.BigInteger;
import java.util.Collection;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springbootapplication.model.Greeting;
import com.springbootapplication.service.EmailService;
import com.springbootapplication.service.GreetingService;

/**
 * @author Ashu Rest Controller extends Controller. This converts object to
 *         json/xml.
 */
@RestController
public class GreetingController extends BaseController{
	@Autowired
	private GreetingService greetingService;
	@Autowired
	private EmailService emailService;

	@RequestMapping(value = "/api/greetings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Greeting>> getGreetings() {
		System.out.println("Entered");
		Collection<Greeting> collectionofGreetings = greetingService.findAll();
		return new ResponseEntity<Collection<Greeting>>(collectionofGreetings,
				HttpStatus.OK);
	}

	@RequestMapping(value = "api/greetings/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> getGreetingsbyId(@PathVariable("id") Long id) {
		Greeting greeting = greetingService.findOne(id);
		if (null == greeting) {
			return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
	}

	@RequestMapping(value = "api/greetings", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> createGreeting(
			@RequestBody Greeting greeting) {
		Greeting greetings = greetingService.createGreeting(greeting);
		return new ResponseEntity<Greeting>(greetings, HttpStatus.CREATED);

	}

	@RequestMapping(value = "api/greetings/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> updateGreeting(
			@RequestBody Greeting greeting, @PathVariable("id") BigInteger id) {
		Greeting updatedGreeting = greetingService.updateGreeting(greeting);
		if (updatedGreeting == null) {
			return new ResponseEntity<Greeting>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Greeting>(updatedGreeting, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/api/greetings/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Greeting> deleteGreeting(
			@RequestBody Greeting greeting, @PathVariable("id") Long id) {
		greetingService.deleteGreeting(id);
		/*
		 * if (deleted) { return new ResponseEntity<Greeting>(
		 * HttpStatus.INTERNAL_SERVER_ERROR); }
		 */
		return new ResponseEntity<Greeting>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/api/greetings/{id}/send", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> sendGreeting(
			@PathVariable("id") Long id,
			@RequestParam(value = "wait", defaultValue = "false") boolean waitForAsyncResult) {

		Greeting greeting = null;

		try {
			greeting = greetingService.findOne(id);
			if (greeting == null) {
				return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
			}

			if (waitForAsyncResult) {
				Future<Boolean> asyncResponse = emailService
						.sendAyncWithResult(greeting);
				boolean emailSent = asyncResponse.get();
			} else {
				emailService.sendAsync(greeting);
			}
		} catch (Exception e) {
			return new ResponseEntity<Greeting>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
	}
}

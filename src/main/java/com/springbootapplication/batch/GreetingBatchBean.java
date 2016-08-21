package com.springbootapplication.batch;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.springbootapplication.model.Greeting;
import com.springbootapplication.service.GreetingService;

@Component
//only run when batch profile is active
@Profile(value="batch")
public class GreetingBatchBean {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GreetingService greetingService;

	@Scheduled(cron = "${batch.greeting.cron}")
	public void cronJob() {
		logger.info("Cron Job");
		Collection<Greeting> greetingCollection = greetingService.findAll();
		logger.info("Collection of greetings size is {}"
				+ greetingCollection.size());
		logger.info("Cron Job");
	}
//Time of next execution is from start of previous job
	//@Scheduled(initialDelay = 5000, fixedRate = 15000)
	@Scheduled(initialDelayString = "${batch.greeting.initialdelay}", fixedRateString = "${batch.greeting.fixedrate}")
	public void fixedRateJobWithInitialDelay() {
		logger.info("> fixedRateJobWithInitialDelay");

		// Add scheduled logic here
		// Simulate job processing time
		long pause = 5000;
		long start = System.currentTimeMillis();
		do {
			if (start + pause < System.currentTimeMillis()) {
				break;
			}
		} while (true);
		logger.info("Processing time was {} seconds.", pause / 1000);

		logger.info("< fixedRateJobWithInitialDelay");
	}
//Time of next execution is from end of previous job
	//@Scheduled(initialDelay = 5000, fixedDelay = 15000)
	@Scheduled(initialDelayString = "${batch.greeting.initialdelay}", fixedDelayString = "${batch.greeting.fixeddelay}")

	public void fixedDelayJobWithInitialDelay() {
		logger.info("> fixedDelayJobWithInitialDelay");

		// Add scheduled logic here
		// Simulate job processing time
		long pause = 5000;
		long start = System.currentTimeMillis();
		do {
			if (start + pause < System.currentTimeMillis()) {
				break;
			}
		} while (true);
		logger.info("Processing time was {} seconds.", pause / 1000);

		logger.info("< fixedDelayJobWithInitialDelay");
	}

}

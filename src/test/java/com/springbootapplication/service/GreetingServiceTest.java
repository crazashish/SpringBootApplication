package com.springbootapplication.service;

import java.util.Collection;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.springbootapplication.AbstractTest;
import com.springbootapplication.model.Greeting;

@Transactional
public class GreetingServiceTest extends AbstractTest {

	@Autowired
	private GreetingService greetingService;

	@Before
	public void setUp() {
		greetingService.evictCache();
	}

	@After
	public void tearDown() {

	}

	@Test
	public void testfindAll() {
		Collection<Greeting> greetingCollection = greetingService.findAll();
		Assert.assertNotNull("Failure:Expected Not Null", greetingCollection);
		Assert.assertEquals("Failure:Expected Size", 2,
				greetingCollection.size());
	}

	@Test
	public void testFindOne() {
		Long id = new Long(1);
		Greeting entity = greetingService.findOne(id);
		Assert.assertNotNull("failure - expected not null", entity);
		Assert.assertEquals("failure - expected id attribute match", id,
				entity.getId());
	}

	@Test
	public void testFindOneNotFound() {
		Long id = Long.MAX_VALUE;
		Greeting entity = greetingService.findOne(id);
		Assert.assertNull("failure - expected null", entity);
	}

	@Test
	public void testCreate() {
		Greeting entity = new Greeting();
		entity.setName("test");
		Greeting createdEntity = greetingService.createGreeting(entity);
		Assert.assertNotNull("failure - expected not null", createdEntity);
		Assert.assertNotNull("failure - expected id attribute not null",
				createdEntity.getId());
		Assert.assertEquals("failure - expected text attribute match", "test",
				createdEntity.getName());
		Collection<Greeting> list = greetingService.findAll();
		Assert.assertEquals("failure - expected size", 3, list.size());
	}

	@Test
	public void testCreateWithId() {
		Exception exception = null;
		Greeting entity = new Greeting();
		entity.setId(Long.MAX_VALUE);
		entity.setName("test");
		try {
			greetingService.createGreeting(entity);
		} catch (EntityExistsException e) {
			exception = e;
		}
		Assert.assertNotNull("failure - expected exception", exception);
		Assert.assertTrue("failure - expected EntityExistsException",
				exception instanceof EntityExistsException);

	}

	@Test
	public void testUpdate() {
		Long id = new Long(1);
		Greeting entity = greetingService.findOne(id);
		Assert.assertNotNull("failure - expected not null", entity);
		String updatedText = entity.getName() + " test";
		entity.setName(updatedText);
		Greeting updatedEntity = greetingService.updateGreeting(entity);

		Assert.assertNotNull("failure - expected not null", updatedEntity);
		Assert.assertEquals("failure - expected id attribute match", id,
				updatedEntity.getId());
		Assert.assertEquals("failure - expected text attribute match",
				updatedText, updatedEntity.getName());
	}

	@Test
	public void testUpdateNotFound() {
		Exception exception = null;
		Greeting entity = new Greeting();
		entity.setId(Long.MAX_VALUE);
		entity.setName("test");
		try {
			greetingService.updateGreeting(entity);
		} catch (NoResultException e) {
			exception = e;
		}
		Assert.assertNotNull("failure - expected exception", exception);
		Assert.assertTrue("failure - expected NoResultException",
				exception instanceof NoResultException);

	}

	@Test
	public void testDelete() {
		Long id = new Long(1);
		Greeting entity = greetingService.findOne(id);
		Assert.assertNotNull("failure - expected not null", entity);
		greetingService.deleteGreeting(id);
		Collection<Greeting> list = greetingService.findAll();
		Assert.assertEquals("failure - expected size", 1, list.size());
		Greeting deletedEntity = greetingService.findOne(id);
		Assert.assertNull("failure - expected null", deletedEntity);
	}

}

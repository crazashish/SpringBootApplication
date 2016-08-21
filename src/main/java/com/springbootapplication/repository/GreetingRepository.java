package com.springbootapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springbootapplication.model.Greeting;

/**
 * @author Ashu
 *
 */
@Repository("greetingRepository")
public interface GreetingRepository extends JpaRepository<Greeting, Long> {

}

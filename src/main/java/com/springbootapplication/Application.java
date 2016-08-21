package com.springbootapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 * @author Ashu
 * Spring Boot Application Starting point
 *
 */
@SpringBootApplication
//enable transaction management
@EnableTransactionManagement
//enable caching
@EnableCaching
//enable scheduling
@EnableScheduling
//enable async process
@EnableAsync
public class Application 
{
    public static void main( String[] args )
    {
        SpringApplication.run(Application.class, args);
    }
    @Bean
    public CacheManager cacheManager(){
    	//Below is suitable only for non production environment
    	//ConcurrentMapCacheManager cacheManager=new ConcurrentMapCacheManager("greetings");
    	GuavaCacheManager cacheManager=new GuavaCacheManager("greetings");
    	return cacheManager;
    }
}

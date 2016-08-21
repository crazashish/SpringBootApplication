package com.springbootapplication.exception;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface ExceptionAttributes {
	
	Map<String,Object> getExceptionAttributes(Exception e,HttpServletRequest req,org.springframework.http.HttpStatus status);

}

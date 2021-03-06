/**
 * 
 */
package com.github.ricardobaumann.spring_crm.controllers;

import com.github.ricardobaumann.spring_crm.dtos.ErrorDTO;
import com.github.ricardobaumann.spring_crm.exceptions.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

/**
 * Controller advice to handle exceptions and map it to http status
 * @author ricardobaumann
 *
 */
@RestController
@ControllerAdvice
public class ExceptionMapper {

	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(value = {ValidationException.class,
    		ConstraintViolationException.class, 
    		DataIntegrityViolationException.class,
    		TransactionSystemException.class})
    public @ResponseBody
	ErrorDTO handleUnprocessableEntity(Throwable ex) {
       return new ErrorDTO(ex.getCause()!=null ? ex.getCause().getMessage() : ex.getMessage());//TODO handle a better and understable message
    }
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public @ResponseBody
	ErrorDTO handleException(Throwable t) {
		return new ErrorDTO(t.getCause().getMessage());
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public @ResponseBody
	ErrorDTO handleNotFound(Throwable t) {
		return new ErrorDTO("Resource not found");
	}


}

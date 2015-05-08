package org.rjung.data.resources;

import org.rjung.data.errors.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;

@RestController
@ControllerAdvice
public class ExceptionResource {
	final static Logger LOG = LoggerFactory.getLogger(ExceptionResource.class);

	@ExceptionHandler({ Exception.class })
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public ErrorMessage handleConstraintViolationException(
			ConstraintViolationException e) {
		LOG.error(e.getMessage(), e);
		return new ErrorMessage("Internal Server Error", null, 500);
	}

}

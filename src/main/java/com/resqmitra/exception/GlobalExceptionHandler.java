package com.resqmitra.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.resqmitra.dto.ErrorResponse;
import com.resqmitra.module.auth.exception.UnauthorizedUserException;
import com.resqmitra.module.auth.exception.UserIdAndPasswordNotMatchException;
import com.resqmitra.module.incident.exception.IncidentNotFoundException;
import com.resqmitra.module.user.exception.UserAlreadyCreatedException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler({ UsernameNotFoundException.class  , IncidentNotFoundException.class})
	public ResponseEntity<ErrorResponse> handleNotFound(RuntimeException ex, HttpServletRequest request) {
		return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request.getRequestURI());
	}
	
	@ExceptionHandler({ UserAlreadyCreatedException.class })
public ResponseEntity<ErrorResponse> handleConflict(RuntimeException ex, HttpServletRequest request) {
	return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT, request.getRequestURI());
}
	
	@ExceptionHandler({ UnauthorizedUserException.class, UserIdAndPasswordNotMatchException.class })
	public ResponseEntity<ErrorResponse> handleUnauthorized(RuntimeException ex, HttpServletRequest request) {
		return buildErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED, request.getRequestURI());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex,
			HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.put(error.getField(), error.getDefaultMessage());
		}
		ErrorResponse err = new ErrorResponse(LocalDateTime.now(), "Validation failed", request.getRequestURI());
		err.setValidationErrors(errors);
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
		log.error("Unhandled exception", ex);
		return buildErrorResponse("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR, request.getRequestURI());
	}

	private ResponseEntity<ErrorResponse> buildErrorResponse(String message, HttpStatus status, String path) {
		ErrorResponse err = new ErrorResponse(LocalDateTime.now(), message, path);
		return new ResponseEntity<>(err, status);
	}

}

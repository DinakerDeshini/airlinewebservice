package com.ding.airlinewebservice.exception;

import com.sun.jmx.snmp.ThreadContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice(basePackages = "com.ding.airlinewebservice")
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

       List<String> errors = ex.getBindingResult().getAllErrors().stream()
               .map(e->e.getDefaultMessage()).collect(Collectors.toList());

        HttpServletRequest req = ((ServletWebRequest)request).getRequest();

        ApiErrorResponse apiErrorResponse = ApiErrorResponseHandler.getInstance()
                //.withErrorId("Airlines - " + LocalDateTime.now(ZoneOffset.UTC))
                .withErrorId("Airlines - "+ThreadContext.get("requestId"))
                .withStatus(status.value())
                .withMessage(ex.getMessage())
                .withErrors(errors)
                .forPath(req.getRequestURI())
                .build();

        return new ResponseEntity<Object>(apiErrorResponse,headers,status);
    }

    @ExceptionHandler(value = ResponseStatusException.class)
    public ResponseEntity<ApiErrorResponse> handleResponseStatusException(ResponseStatusException ex, HttpServletRequest request) {

        List<String> errors = Arrays.asList(ex.getReason());

        ApiErrorResponse apiErrorResponse = ApiErrorResponseHandler.getInstance()
                //.withErrorId("Airlines - " + LocalDateTime.now(ZoneOffset.UTC))
                .withErrorId("Airlines - "+ThreadContext.get("requestId"))
                .withStatus(ex.getStatus().value())
                .withMessage(ex.getMessage())
                .withErrors(errors)
                .forPath(request.getRequestURI())
                .build();

        return new ResponseEntity<ApiErrorResponse>(apiErrorResponse,ex.getStatus());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {

        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violations: ex.getConstraintViolations()) {
            errors.add(violations.getMessage());
        }

        ApiErrorResponse apiErrorResponse = ApiErrorResponseHandler.getInstance()
                //.withErrorId("Airlines - " + LocalDateTime.now(ZoneOffset.UTC))
                .withErrorId("Airlines - "+ThreadContext.get("requestId"))
                .withStatus(HttpStatus.BAD_REQUEST.value())
                .withMessage(ex.getMessage())
                .withErrors(errors)
                .forPath(request.getRequestURI())
                .build();

        return new ResponseEntity<ApiErrorResponse>(apiErrorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception ex, HttpServletRequest request) {

        List<String> errors = Arrays.asList(ex.getMessage());

        ApiErrorResponse apiErrorResponse = ApiErrorResponseHandler.getInstance()
                //.withErrorId("Airlines - " + LocalDateTime.now(ZoneOffset.UTC))
                .withErrorId("Airlines - "+ThreadContext.get("requestId"))
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .withMessage(ex.getMessage())
                .withErrors(errors)
                .forPath(request.getRequestURI())
                .build();

        return new ResponseEntity<ApiErrorResponse>(apiErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

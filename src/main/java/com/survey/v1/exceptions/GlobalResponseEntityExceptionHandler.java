package com.survey.v1.exceptions;

import org.springframework.lang.NonNull;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.survey.v1.models.response.FailureResponse;

import jakarta.validation.ConstraintViolationException;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

        @Override
        protected ResponseEntity<Object> handleNoHandlerFoundException(
                        @NonNull NoHandlerFoundException ex,
                        @NonNull HttpHeaders headers,
                        @NonNull HttpStatusCode status,
                        @NonNull WebRequest request) {

                List<String> errors = new ArrayList<>();
                errors.add("No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL());

                FailureResponse<Object> failureResponse = new FailureResponse<>(
                                HttpStatus.NOT_FOUND.value(),
                                "Not Found",
                                "The requested resource does not exist",
                                errors);

                return new ResponseEntity<>(failureResponse, HttpStatus.NOT_FOUND);
        }

        @Override
        protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
                        @NonNull HttpRequestMethodNotSupportedException ex,
                        @NonNull HttpHeaders headers,
                        @NonNull HttpStatusCode status,
                        @NonNull WebRequest request) {

                List<String> errors = new ArrayList<>();
                errors.add("Method '" + ex.getMethod() + "' is not supported for this request. Supported methods are: "
                                + ex.getSupportedHttpMethods());

                FailureResponse<Object> failureResponse = new FailureResponse<>(
                                HttpStatus.METHOD_NOT_ALLOWED.value(),
                                "Method Not Allowed",
                                "The requested method is not allowed for this endpoint",
                                errors);

                return new ResponseEntity<>(failureResponse, HttpStatus.METHOD_NOT_ALLOWED);
        }

        @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(
                        @NonNull MethodArgumentNotValidException ex,
                        @NonNull HttpHeaders headers,
                        @NonNull HttpStatusCode status,
                        @NonNull WebRequest request) {
                List<String> errors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                .collect(Collectors.toList());

                FailureResponse<Object> failureResponse = new FailureResponse<>(
                                HttpStatus.BAD_REQUEST.value(),
                                "Validation Failed",
                                "The submitted data failed validation",
                                errors);

                return new ResponseEntity<>(failureResponse, HttpStatus.BAD_REQUEST);
        }

        @Override
        protected ResponseEntity<Object> handleHttpMessageNotReadable(
                        @NonNull HttpMessageNotReadableException ex,
                        @NonNull HttpHeaders headers,
                        @NonNull HttpStatusCode status,
                        @NonNull WebRequest request) {
                List<String> errors = new ArrayList<>();
                errors.add("Required request body is missing or malformed");

                FailureResponse<Object> failureResponse = new FailureResponse<>(
                                HttpStatus.BAD_REQUEST.value(),
                                "Bad Request",
                                "Failed to read request body",
                                errors);

                return new ResponseEntity<>(failureResponse, HttpStatus.BAD_REQUEST);
        }

        @Override
        protected ResponseEntity<Object> handleMissingServletRequestParameter(
                        @NonNull MissingServletRequestParameterException ex, @NonNull HttpHeaders headers,
                        @NonNull HttpStatusCode status,
                        @NonNull WebRequest request) {
                List<String> errors = new ArrayList<>();
                errors.add("Required parameter '" + ex.getParameterName() + "' is missing");

                FailureResponse<Object> failureResponse = new FailureResponse<>(
                                HttpStatus.BAD_REQUEST.value(),
                                "Bad Request",
                                "Missing required parameter",
                                errors);

                return new ResponseEntity<>(failureResponse, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<FailureResponse<Object>> handleConstraintViolation(
                        ConstraintViolationException ex, WebRequest request) {
                List<String> errors = ex.getConstraintViolations()
                                .stream()
                                .map(violation -> violation.getMessage())
                                .collect(Collectors.toList());

                FailureResponse<Object> failureResponse = new FailureResponse<>(
                                HttpStatus.BAD_REQUEST.value(),
                                "Validation Failed",
                                "One or more fields failed validation",
                                errors);

                return new ResponseEntity<>(failureResponse, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity<FailureResponse<Object>> handleMethodArgumentTypeMismatch(
                        MethodArgumentTypeMismatchException ex, WebRequest request) {
                List<String> errors = new ArrayList<>();
                errors.add("Invalid " + ex.getName() + ": " + ex.getValue());

                FailureResponse<Object> failureResponse = new FailureResponse<>(
                                HttpStatus.BAD_REQUEST.value(),
                                "Bad Request",
                                "Invalid argument type",
                                errors);

                return new ResponseEntity<>(failureResponse, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<FailureResponse<Object>> handleIllegalArgumentException(IllegalArgumentException ex,
                        WebRequest request) {
                List<String> errors = new ArrayList<>();
                errors.add(ex.getMessage());

                FailureResponse<Object> failureResponse = new FailureResponse<>(
                                HttpStatus.BAD_REQUEST.value(),
                                "Bad Request",
                                "Invalid argument",
                                errors);

                return new ResponseEntity<>(failureResponse, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(NoSuchElementException.class)
        public ResponseEntity<FailureResponse<Object>> handleNoSuchElementException(NoSuchElementException ex,
                        WebRequest request) {
                List<String> errors = new ArrayList<>();
                errors.add("The requested element could not be found");

                FailureResponse<Object> failureResponse = new FailureResponse<>(
                                HttpStatus.NOT_FOUND.value(),
                                "Not Found",
                                ex.getMessage(),
                                errors);

                return new ResponseEntity<>(failureResponse, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<FailureResponse<Object>> handleAllUncaughtException(Exception ex, WebRequest request) {
                List<String> errors = new ArrayList<>();
                errors.add("An unexpected error occurred");

                FailureResponse<Object> failureResponse = new FailureResponse<>(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "Internal server error",
                                ex.getMessage(),
                                errors);

                return new ResponseEntity<>(failureResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(SequenceException.class)
        public ResponseEntity<FailureResponse<Object>> handleUserOperationException(SequenceException ex,
                        WebRequest request) {
                List<String> errors = new ArrayList<>();
                errors.add(ex.getMessage());

                FailureResponse<Object> failureResponse = new FailureResponse<>(
                                ex.getHttpStatus().value(),
                                ex.getErrorTitle(),
                                ex.getDetailMessage(),
                                errors);

                return new ResponseEntity<>(failureResponse, ex.getHttpStatus());
        }

        @ExceptionHandler(QuestionException.class)
        public ResponseEntity<FailureResponse<Object>> handleUserOperationException(QuestionException ex,
                        WebRequest request) {
                List<String> errors = new ArrayList<>();
                errors.add(ex.getMessage());

                FailureResponse<Object> failureResponse = new FailureResponse<>(
                                ex.getHttpStatus().value(),
                                ex.getErrorTitle(),
                                ex.getDetailMessage(),
                                errors);

                return new ResponseEntity<>(failureResponse, ex.getHttpStatus());
        }
}
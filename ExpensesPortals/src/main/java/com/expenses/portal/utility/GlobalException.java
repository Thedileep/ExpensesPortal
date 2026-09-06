package com.expenses.portal.utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.expenses.portal.exception.ExpensesException;

@ControllerAdvice
@RestControllerAdvice
public class GlobalException {

    private static final Log LOGGER = LogFactory.getLog(GlobalException.class);

    private final Environment environment;

    GlobalException(Environment environment) {
        this.environment = environment;
    }

    @ExceptionHandler(ExpensesException.class)
    public ResponseEntity<ErrorInfo> userDataExceptionHandler(ExpensesException exception) {

        LOGGER.error(exception.getMessage(), exception);

        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
        errorInfo.setErrorMessage(exception.getMessage());

        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorInfo> userDataExceptionHandler(UsernameNotFoundException exception) {

        LOGGER.error(exception.getMessage(), exception);

        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
        errorInfo.setErrorMessage(exception.getMessage());

        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> generalExceptionHandler(Exception exception) {

        LOGGER.error(exception.getMessage(), exception);

        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorInfo.setErrorMessage( environment.getProperty("General.EXCEPTION_MESSAGE"));

        return new ResponseEntity<>(errorInfo,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
package com.expenses.portal.utility;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(LoggingAspect.class);

    @AfterThrowing(pointcut = "execution(* com.expenses.portal.service.*.*(..))",throwing = "exception")
    public void logServiceException(Exception exception) {

        LOGGER.error("Exception in service layer: {}",
            exception.getMessage(),exception);
    }
}
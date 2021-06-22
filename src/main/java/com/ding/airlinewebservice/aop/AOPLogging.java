package com.ding.airlinewebservice.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Configuration
public class AOPLogging {

    private static final Logger logger = LogManager.getLogger(AOPLogging.class);

    @Pointcut("within(com.ding.airlinewebservice..*)")
    public void applicationControllerPackage() {

    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void applicationControllerBean() {

    }

    @Around("applicationControllerBean() && applicationControllerPackage()")
    public Object aroundLogging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        logger.debug("Request for {}.{}() with argument[s]={}",proceedingJoinPoint.getSignature().getDeclaringType(),
                proceedingJoinPoint.getSignature().getName(), Arrays.toString(proceedingJoinPoint.getArgs()));

        Instant start = Instant.now();

        Object returnValue = proceedingJoinPoint.proceed();

        Instant finish = Instant.now();

        long duration = Duration.between(start, finish).toMillis();

        logger.debug("Response for {}.{} is {}", proceedingJoinPoint.getSignature().getDeclaringType(),
                proceedingJoinPoint.getSignature().getName(),returnValue);

        logger.info("Execution Time for {}.{} is {}",proceedingJoinPoint.getSignature().getDeclaringType(),
                proceedingJoinPoint.getSignature().getName(),
                new SimpleDateFormat("mm:ss:SSSS").format(new Date(duration)));

        return returnValue;
    }

    @Pointcut("within(com.ding.airlinewebservice..*)")
//    @Pointcut("execution(* com.ding.airlinewebservice.controller.FlightController.*(..)")
    public void applicationExceptionPackage() {

    }

    @AfterThrowing(pointcut ="applicationExceptionPackage()", throwing = "e")
    public void logAfterExceptionThrown(JoinPoint joinPoint, Throwable e) {
        logger.debug("Exception occurred for {}.{} with {} and {}", joinPoint.getSignature().getDeclaringType(),
                joinPoint.getSignature().getName(),
                e.getCause()!=null ? e.getCause() : "NULL",
                e.getMessage()!=null ? e.getMessage() : "NULL");
    }
}

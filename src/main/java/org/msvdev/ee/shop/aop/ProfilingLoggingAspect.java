package org.msvdev.ee.shop.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Slf4j
@Aspect
@Component
public class ProfilingLoggingAspect {


    @Around("execution(public * org.msvdev.ee.shop.controller.*.*(..))")
    public Object controllerProfiling(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        long startTime = System.nanoTime();
        Object out = proceedingJoinPoint.proceed();
        long duration = (System.nanoTime() - startTime) / 1000;

        log.info(String.format("%s duration: %d us", proceedingJoinPoint.getSignature(), duration));

        return out;
    }

}
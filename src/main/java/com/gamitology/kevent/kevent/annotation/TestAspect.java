package com.gamitology.kevent.kevent.annotation;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TestAspect {

    @Around("@annotation(TestAnnotation)")
    public void runTest() {
        System.out.println("TEST");
    }
}

package com.project.matchimban.common.global.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {

    String key();

    TimeUnit timeUnit() default TimeUnit.SECONDS;

    long waitTime() default 10L; //대기시간

    long leaseTime() default 3L; //해제시간
}
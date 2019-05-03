package com.j4u.annotationlib.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface FirstAnnotation {
    String hello() default "hello first";
}

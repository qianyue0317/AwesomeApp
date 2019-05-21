package com.qianyue.annotation_api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface FirstAnnotation {
    String hello() default "hello first";
}

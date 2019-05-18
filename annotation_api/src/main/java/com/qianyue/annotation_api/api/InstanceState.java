package com.qianyue.annotation_api.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
//@Retention(RetentionPolicy.CLASS)
@Retention(RetentionPolicy.SOURCE)
public @interface InstanceState {
    String key();
}

package com.qy.j4u;

import com.qianyue.annotation_api.api.FirstAnnotation;

@FirstAnnotation(hello = "first yeah")
public class TestMyAnnotation {

    String content;

    public native String  getContent();


    static {
        System.loadLibrary("native-lib");
    }

}

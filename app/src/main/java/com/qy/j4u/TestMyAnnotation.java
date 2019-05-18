package com.qy.j4u;

import com.qianyue.annotation_api.api.FirstAnnotation;
import com.qianyue.annotation_api.api.InstanceState;

@FirstAnnotation(hello = "first yeah")
public class TestMyAnnotation {

    @InstanceState(key = "1236")
    String content;

    public native String  getContent();


    static {
        System.loadLibrary("native-lib");
    }

}

package com.qy.j4u.utils.collectionutil;

import java.util.ArrayList;
import java.util.List;

/**
 *  集合工具封装 , 完全封装jdk中的集合 不适用jdk中的集合
 */
public class CollectionKit {
    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList<>();
    }

    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

}

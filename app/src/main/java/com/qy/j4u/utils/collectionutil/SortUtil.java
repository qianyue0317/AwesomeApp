package com.qy.j4u.utils.collectionutil;


import com.qy.j4u.utils.JLog;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 排序工具类
 * Created by abc on 2016/11/18.
 */

public class SortUtil {


    /**
     * 根据指定的字段对bean排序
     * @param data  要排序的集合
     * @param sortKey   要排序的依据字段
     * @param <T>   bean的泛型
     * @param <V>   指定字段类型的泛型
     */
    public static <T,V extends Comparable<V>> void sortList(List<T> data, final String sortKey) {
        if (data == null || data.size() == 0 || data.size() == 1) {
            return;
        }

        Collections.sort(data, new Comparator<T>() {
            @Override
            public int compare(T lhs, T rhs) {
                Class<?> tClass = lhs.getClass();
                try {
                    Field field = tClass.getDeclaredField(sortKey);
                    field.setAccessible(true);
                    V lhsV = (V) field.get(lhs);
                    V rhsV = (V) field.get(rhs);
                    return lhsV.compareTo(rhsV);
                } catch (NoSuchFieldException e) {
//                    e.printStackTrace();
                    JLog.d("排序方法:没有此Field");
                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
                    JLog.d("非法的访问权限");
                }
                return 0;
            }
        });
    }



}

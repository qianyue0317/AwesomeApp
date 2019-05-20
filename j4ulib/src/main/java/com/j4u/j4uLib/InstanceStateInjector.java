package com.j4u.j4uLib;

import android.os.Bundle;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InstanceStateInjector {

    private static Map<String, InstanceStateInjectorInterface> sInjectorContainer;
    private static Set<String> sNoInjectorKeys;

    static {
        sNoInjectorKeys = new HashSet<>();
        sInjectorContainer = new HashMap<>();
    }

    public static void save(Object activity, Bundle savedInstanceState) {
        Class<?> aClass = activity.getClass();
        if (sNoInjectorKeys.contains(aClass.getSimpleName())) {
            return;
        }
        InstanceStateInjectorInterface instanceStateInjectorInterface;
        instanceStateInjectorInterface = sInjectorContainer.get(aClass.getSimpleName());
        if (instanceStateInjectorInterface == null) {
            try {
                String className =
                        aClass.getCanonicalName() + "$$InstanceStateHolder";
                @SuppressWarnings("unchecked") Class<? extends InstanceStateInjectorInterface> clz =
                        (Class<? extends InstanceStateInjectorInterface>) Class.forName(className);
                Constructor<? extends InstanceStateInjectorInterface> constructor =
                        clz.getConstructor();
                instanceStateInjectorInterface =
                        constructor.newInstance();
                sInjectorContainer.put(aClass.getSimpleName(),
                        instanceStateInjectorInterface);
            } catch (ClassNotFoundException e) {
                sNoInjectorKeys.add(aClass.getSimpleName());
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        if (instanceStateInjectorInterface == null) {
            return;
        }
        instanceStateInjectorInterface.save(savedInstanceState, activity);
    }

    public static void restore(Object activity, Bundle savedInstanceState) {
        InstanceStateInjectorInterface instanceStateInjectorInterface =
                sInjectorContainer.get(activity.getClass().getSimpleName());
        if (savedInstanceState != null && instanceStateInjectorInterface != null) {
            instanceStateInjectorInterface.restore(savedInstanceState, activity);
        }
    }


}

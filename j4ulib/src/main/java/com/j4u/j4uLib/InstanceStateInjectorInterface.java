package com.j4u.j4uLib;

import android.os.Bundle;

public interface InstanceStateInjectorInterface {
    void save(Bundle savedInstanceState, Object activity);

    void restore(Bundle savedInstanceState, Object activity);
}

package com.xmartlabs.scasas.doapp.ui;

import android.support.annotation.NonNull;

import com.f2prateek.dart.HensonNavigable;
import com.f2prateek.dart.InjectExtra;
import com.xmartlabs.scasas.doapp.model.Group;
import com.xmartlabs.scasas.doapp.model.User;

/**
 * Created by santiago on 3/15/17.
 */
public class TasksListActivity extends SingleFragmentActivity {
    @NonNull
    @InjectExtra
    User user;

    @NonNull
    @InjectExtra
    Group group;

    @NonNull
    @Override
    protected BaseFragment createFragment() {
        return new TasksListFragmentBuilder(group, user).build();
    }
}

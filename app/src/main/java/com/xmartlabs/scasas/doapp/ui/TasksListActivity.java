package com.xmartlabs.scasas.doapp.ui;

import android.support.annotation.NonNull;

import com.f2prateek.dart.HensonNavigable;

/**
 * Created by santiago on 3/15/17.
 */
@HensonNavigable
public class TasksListActivity extends SingleFragmentActivity {
    @NonNull
    @Override
    protected BaseFragment createFragment() {
        return new TasksListFragmentBuilder().build();
    }
}

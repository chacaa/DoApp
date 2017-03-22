package com.xmartlabs.scasas.doapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.f2prateek.dart.HensonNavigable;
import com.f2prateek.dart.InjectExtra;
import com.xmartlabs.scasas.doapp.R;
import com.xmartlabs.scasas.doapp.model.Group;
import com.xmartlabs.scasas.doapp.model.User;

import butterknife.BindView;

/**
 * Created by santiago on 3/15/17.
 */
@HensonNavigable
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

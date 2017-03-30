package com.xmartlabs.scasas.doapp.ui.tasklist;

import android.support.annotation.NonNull;

import com.f2prateek.dart.InjectExtra;
import com.xmartlabs.scasas.doapp.model.Group;
import com.xmartlabs.scasas.doapp.model.User;
import com.xmartlabs.scasas.doapp.ui.BaseFragment;
import com.xmartlabs.scasas.doapp.ui.SingleFragmentActivity;

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

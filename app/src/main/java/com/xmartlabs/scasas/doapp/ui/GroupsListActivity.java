package com.xmartlabs.scasas.doapp.ui;

import android.support.annotation.NonNull;

import com.f2prateek.dart.HensonNavigable;
import com.f2prateek.dart.InjectExtra;
import com.xmartlabs.scasas.doapp.model.User;

/**
 * Created by scasas on 3/20/17.
 */
public class GroupsListActivity extends SingleFragmentActivity {
  @NonNull
  @InjectExtra
  User user;

  @NonNull
  @Override
  protected BaseFragment createFragment() {
    return new GroupsListFragmentBuilder(user).build();
  }
}

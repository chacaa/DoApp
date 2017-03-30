package com.xmartlabs.scasas.doapp.ui.grouplist;

import android.support.annotation.NonNull;

import com.f2prateek.dart.HensonNavigable;
import com.f2prateek.dart.InjectExtra;
import com.xmartlabs.scasas.doapp.model.User;
import com.xmartlabs.scasas.doapp.ui.BaseFragment;
import com.xmartlabs.scasas.doapp.ui.SingleFragmentActivity;

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

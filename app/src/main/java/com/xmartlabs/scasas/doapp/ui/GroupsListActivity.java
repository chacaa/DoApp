package com.xmartlabs.scasas.doapp.ui;

import android.support.annotation.NonNull;

import com.f2prateek.dart.HensonNavigable;

/**
 * Created by scasas on 3/20/17.
 */
@HensonNavigable
public class GroupsListActivity extends SingleFragmentActivity {
  @NonNull
  @Override
  protected BaseFragment createFragment() {
    return new GroupsListFragmentBuilder().build();
  }
}

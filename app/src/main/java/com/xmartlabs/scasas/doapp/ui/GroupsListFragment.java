package com.xmartlabs.scasas.doapp.ui;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.xmartlabs.scasas.doapp.R;

/**
 * Created by scasas on 3/20/17.
 */
@FragmentWithArgs
public class GroupsListFragment extends BaseFragment {

  @Override
  protected int getLayoutResId() {
    return R.layout.list_groups_fragment;
  }
}

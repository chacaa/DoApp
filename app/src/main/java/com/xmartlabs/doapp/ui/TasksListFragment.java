package com.xmartlabs.doapp.ui;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.xmartlabs.template.R;

/**
 * Created by santiago on 3/15/17.
 */
@FragmentWithArgs
public class TasksListFragment extends BaseFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.list_tasks_fragment;
    }
}

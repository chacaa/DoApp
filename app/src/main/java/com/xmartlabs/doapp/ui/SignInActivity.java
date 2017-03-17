package com.xmartlabs.doapp.ui;

import android.support.annotation.NonNull;

import com.f2prateek.dart.HensonNavigable;

/**
 * Created by scasas on 3/8/17.
 */
@HensonNavigable
public class SignInActivity extends SingleFragmentActivity {
  @NonNull
  @Override
  protected BaseFragment createFragment() {
    return new SignInFragmentBuilder().build();
  }
}

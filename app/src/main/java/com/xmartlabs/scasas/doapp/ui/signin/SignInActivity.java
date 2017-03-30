package com.xmartlabs.scasas.doapp.ui.signin;

import android.support.annotation.NonNull;

import com.f2prateek.dart.HensonNavigable;
import com.xmartlabs.scasas.doapp.ui.BaseFragment;
import com.xmartlabs.scasas.doapp.ui.SingleFragmentActivity;

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

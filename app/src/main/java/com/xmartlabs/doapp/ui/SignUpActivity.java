package com.xmartlabs.doapp.ui;

import android.support.annotation.NonNull;

import com.f2prateek.dart.HensonNavigable;

/**
 * Created by santiago on 3/9/17.
 */
@HensonNavigable
public class SignUpActivity extends SingleFragmentActivity {
    @NonNull
    @Override
    protected BaseFragment createFragment() {
        return new SignUpFragmentBuilder().build();
    }
}

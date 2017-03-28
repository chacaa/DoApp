package com.xmartlabs.scasas.doapp.ui.signup;

import android.support.annotation.NonNull;

import com.f2prateek.dart.HensonNavigable;
import com.xmartlabs.scasas.doapp.ui.BaseFragment;
import com.xmartlabs.scasas.doapp.ui.SingleFragmentActivity;

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

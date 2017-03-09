package com.xmartlabs.doapp.ui;

import android.content.Intent;

import com.xmartlabs.template.R;

import butterknife.OnClick;

/**
 * Created by santiago on 3/9/17.
 */

public class SingupFragment extends BaseFragment {
    @Override
    protected int getLayoutResId() {
        return R.layout.singup_fragment;
    }

    @OnClick(R.id.sign_in)
    void onClickedSingIn() {
        Intent intent = Henson.with(getContext()).gotoSinginActivity().build();
        getContext().startActivity(intent);
    }
}

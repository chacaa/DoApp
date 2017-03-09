package com.xmartlabs.doapp.ui;

import android.content.Intent;
import android.widget.EditText;

import com.xmartlabs.template.R;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by scasas on 3/8/17.
 */
public class SinginFragment extends BaseFragment {
  @BindView(R.id.editTextPassword)
  EditText password;
  @BindView(R.id.editTextUser)
  EditText user;

  @Override
  protected int getLayoutResId() {
    return R.layout.singin_fragment;
  }

  @OnClick(R.id.sign_up)
  void onClickedSignUp() {
    Intent intent = Henson.with(getContext()).gotoSingupActivity().build();
    getContext().startActivity(intent);
  }

  @OnClick(R.id.sign_in)
  void onClickedSingIn() {
    //TODO
  }

  @OnTextChanged(R.id.editTextUser)
  void onUserTextChanged() {
    //TODO
  }
}

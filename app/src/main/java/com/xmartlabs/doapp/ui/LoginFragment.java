package com.xmartlabs.doapp.ui;

import android.widget.EditText;

import com.xmartlabs.template.R;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by scasas on 3/8/17.
 */
public class LoginFragment extends BaseFragment {
  @BindView(R.id.editTextPassword)
  EditText password;
  @BindView(R.id.editTextUser)
  EditText user;

  @Override
  protected int getLayoutResId() {
    return R.layout.login_fragment;
  }

  @OnClick(R.id.sign_up)
  void onClickedSignUp() {
    //TODO
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

package com.xmartlabs.doapp.ui;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.widget.EditText;

import com.xmartlabs.doapp.controller.UserController;
import com.xmartlabs.doapp.model.User;
import com.xmartlabs.template.R;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import rx.SingleSubscriber;

/**
 * Created by scasas on 3/8/17.
 */
public class SinginFragment extends BaseFragment {
  @BindView(R.id.editTextPassword)
  EditText password;
  @BindView(R.id.editTextUser)
  EditText username;

  private User user;

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
    if (aFieldIsNotCompleted()) {
      return;
    }
    if (user == null){
      Snackbar.make(getView(), "User doesn't exists", Snackbar.LENGTH_SHORT).show();
      return;
    }
    if (user.getPassword().equals(password.getText().toString())) {
      //TODO
      Snackbar.make(getView(), "It's all good my friend!", Snackbar.LENGTH_SHORT).show();
    } else {
      Snackbar.make(getView(), "Wrong password", Snackbar.LENGTH_SHORT).show();
    }
  }

  @OnTextChanged(R.id.editTextUser)
  void onUserTextChanged(CharSequence userEmailValue) {
    String email = userEmailValue.toString().trim();
    getUser(email);
  }

  private boolean aFieldIsNotCompleted() {
    if (notHasText(username)) {
      Snackbar.make(getView(), "Complete Username field", Snackbar.LENGTH_SHORT).show();
      //noinspection deprecation
      username.setHintTextColor(getResources().getColor(R.color.reddish_pink));
      return true;
    }
    if (notHasText(password)) {
      Snackbar.make(getView(), "Complete Password field", Snackbar.LENGTH_SHORT).show();
      //noinspection deprecation
      password.setHintTextColor(getResources().getColor(R.color.reddish_pink));
      return true;
    }
    return false;
  }

  private boolean notHasText(EditText editText) {
    return editText.getText().toString().isEmpty();
  }


  private void getUser(String userEmail){
    UserController.getInstance().getUser(userEmail).subscribe(new SingleSubscriber<User>() {
      @Override
      public void onSuccess(User userValue) {
        user = userValue;
      }

      @Override
      public void onError(Throwable error) {
        Snackbar.make(getView(), "Wrong email account", Snackbar.LENGTH_SHORT).show();
      }
    });
  }
}

package com.xmartlabs.doapp.ui;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.widget.EditText;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.android.FragmentEvent;

import com.xmartlabs.doapp.controller.UserController;
import com.xmartlabs.doapp.model.User;
import com.xmartlabs.template.R;

import java.util.concurrent.CancellationException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import rx.SingleSubscriber;
import timber.log.Timber;

/**
 * Created by scasas on 3/8/17.
 */
@FragmentWithArgs
public class SignInFragment extends BaseFragment {
  @BindView(R.id.edit_text_password)
  EditText passwordView;
  @BindView(R.id.edit_text_user)
  EditText usernameView;

  @Inject
  UserController userController;

  private User user;

  @LayoutRes
  @Override
  protected int getLayoutResId() {
    return R.layout.singin_fragment;
  }

  @OnClick(R.id.sign_up)
  void onClickedSignUp() {
    Intent intent = Henson.with(getContext()).gotoSignUpActivity().build();
    getContext().startActivity(intent);
  }

  @OnClick(R.id.sign_in)
  void onClickedSingIn() {
    if (hasAnEmptyField()) {
      return;
    }
    if (user == null) {
      showSnackbarMessage(R.string.user_doesnt_exist);
      return;
    }
    if (user.getPassword().equals(passwordView.getText().toString())) {
      //Intent intent = Henson.with(getContext()).gotoOnBoardingActivity().build(); //This is the right call
      Intent intent = Henson.with(getContext()).gotoTasksListActivity().build(); //TODO: remove it later
      getContext().startActivity(intent);
    } else {
      showSnackbarMessage(R.string.wrong_password);
    }
  }

  @OnTextChanged(R.id.edit_text_user)
  void onUserTextChanged(CharSequence userEmailValue) {
    getUser(userEmailValue.toString().trim());
  }

  private boolean hasAnEmptyField() {
    if (fieldIsEmpty(usernameView)) {
      showSnackbarMessage(R.string.complete_user_field);
      //noinspection deprecation
      usernameView.setHintTextColor(getResources().getColor(R.color.reddish_pink));
      return true;
    }
    if (fieldIsEmpty(passwordView)) {
      showSnackbarMessage(R.string.complete_pass_field);
      //noinspection deprecation
      passwordView.setHintTextColor(getResources().getColor(R.color.reddish_pink));
      return true;
    }
    return false;
  }

  private boolean fieldIsEmpty(EditText editText) {
    return editText.getText().toString().isEmpty();
  }

  private void getUser(String userEmail) {
    userController.getUser(userEmail)
        .compose(RxLifecycle.<User, FragmentEvent>bindUntilEvent(lifecycle(), FragmentEvent.DESTROY_VIEW).forSingle())
        .subscribe(new SingleSubscriber<User>() {
          @Override
          public void onSuccess(User userValue) {
            user = userValue;
          }

          @Override
          public void onError(Throwable error) {
            if (!(error instanceof CancellationException)) {
              //TODO
            }
            Timber.e(error);
          }
        });
  }
}

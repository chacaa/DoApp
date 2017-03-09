package com.xmartlabs.doapp.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.widget.EditText;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.xmartlabs.doapp.DoAppApplication;
import com.xmartlabs.doapp.Gender;
import com.xmartlabs.doapp.controller.UserController;
import com.xmartlabs.doapp.model.User;
import com.xmartlabs.template.R;

import butterknife.BindView;
import butterknife.OnClick;
import rx.SingleSubscriber;
import rx.functions.Action1;

/**
 * Created by santiago on 3/9/17.
 */
public class SingupFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener{
  @BindView(R.id.editTextUser)
  EditText username;
  @BindView(R.id.editTextEmail)
  EditText email;
  @BindView(R.id.editTextBirthday)
  EditText birthday;
  @BindView(R.id.editTextPassword)
  EditText password;

  private User user = createEmptyUser();

  @Override
  protected int getLayoutResId() {
    return R.layout.singup_fragment;
  }

  @OnClick(R.id.sign_in)
  void onClickedSingIn() {
    goToSinginActivity();
  }

  @OnClick(R.id.sign_up)
  void onClickedSignUp() {
    if (aFieldIsNotCompleted()) {
      return;
    }
    setUserValues();
    insertUser();
    //TODO go to onboarding view
  }

  private void goToSinginActivity() {
    Intent intent = Henson.with(getContext()).gotoSinginActivity().build();
    getContext().startActivity(intent);
  }

  private void insertUser() {
    UserController.getInstance().insertUser(user).subscribe(new SingleSubscriber<User>() {
      @Override
      public void onSuccess(User value) {
        //TODO
      }

      @Override
      public void onError(Throwable error) {
        Snackbar.make(getView(), "Failed to create a new account", Snackbar.LENGTH_SHORT).show();
      }
    });
  }

  private User createEmptyUser() {
    return User.builder()
        .name("")
        .email("")
        .gender(Gender.OTHER)
        .birthday(0)
        .build();
  }

  private boolean aFieldIsNotCompleted() {
    if (notHasText(username)) {
      Snackbar.make(getView(), "Complete name field", Snackbar.LENGTH_SHORT).show();
      //noinspection deprecation
      username.setHintTextColor(getResources().getColor(R.color.reddish_pink));
      return true;
    }
    if (notHasText(email)) {
      Snackbar.make(getView(), "Complete name field", Snackbar.LENGTH_SHORT).show();
      //noinspection deprecation
      email.setHintTextColor(getResources().getColor(R.color.reddish_pink));
      return true;
    }
    if (notHasText(birthday)) {
      Snackbar.make(getView(), "Complete name field", Snackbar.LENGTH_SHORT).show();
      //noinspection deprecation
      birthday.setHintTextColor(getResources().getColor(R.color.reddish_pink));
      return true;
    }
    if (notHasText(password)) {
      Snackbar.make(getView(), "Complete name field", Snackbar.LENGTH_SHORT).show();
      //noinspection deprecation
      password.setHintTextColor(getResources().getColor(R.color.reddish_pink));
      return true;
    }
    return false;
  }

  private boolean notHasText(EditText editText) {
    return editText.getText().toString().isEmpty();
  }

  private void setUserValues() {
    user.setName(username.getText().toString().trim());
    user.setEmail(email.getText().toString().trim());
    user.setPassword(password.getText().toString().trim());
    user.setBirthday(Long.parseLong(birthday.getText().toString()));
  }

  @Override
  public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

  }
}

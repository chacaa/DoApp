package com.xmartlabs.doapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import android.view.View;

import android.widget.EditText;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import com.xmartlabs.doapp.Gender;
import com.xmartlabs.doapp.controller.UserController;
import com.xmartlabs.doapp.model.User;
import com.xmartlabs.template.R;

import org.threeten.bp.LocalDate;

import butterknife.BindView;
import butterknife.OnClick;
import rx.SingleSubscriber;

/**
 * Created by santiago on 3/9/17.
 */
public class SingupFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener {
  @BindView(R.id.editTextUser)
  EditText username;
  @BindView(R.id.editTextEmail)
  EditText email;
  @BindView(R.id.texViewBirthday)
  TextView birthday;
  @BindView(R.id.editTextPassword)
  EditText password;

  private User user = createEmptyUser();

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setUpBirthdayTextView();
  }

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

  @OnClick(R.id.texViewBirthday)
  void onClickedBirthday() {
    DatePickerDialog datePickerDialog;
    datePickerDialog = DatePickerDialog.newInstance(
        this,
        user.getBirthday().getYear(),
        user.getBirthday().getMonthValue() - 1,
        user.getBirthday().getDayOfMonth()
    );
    datePickerDialog.show(getActivity().getFragmentManager(), "Date");
  }

  private void goToSinginActivity() {
    getActivity().finish();
  }

  private void insertUser() {
    UserController.getInstance().insertUser(user).subscribe(new SingleSubscriber<User>() {
      @Override
      public void onSuccess(User value) {
        Snackbar.make(getView(), "It's all good my friend", Snackbar.LENGTH_SHORT).show();
        //TODO
      }

      @Override
      public void onError(Throwable error) {
        Snackbar.make(getView(), R.string.failed_create_account, Snackbar.LENGTH_SHORT).show();
      }
    });
  }

  private User createEmptyUser() {
    return User.builder()
        .name("")
        .email("")
        .gender(Gender.OTHER)
        .birthday(LocalDate.now())
        .build();
  }

  private boolean aFieldIsNotCompleted() {
    if (notHasText(username)) {
      Snackbar.make(getView(), R.string.name_field_required, Snackbar.LENGTH_SHORT).show();
      //noinspection deprecation
      username.setHintTextColor(getResources().getColor(R.color.reddish_pink));
      return true;
    }
    if (notHasText(email)) {
      Snackbar.make(getView(), R.string.email_field_required, Snackbar.LENGTH_SHORT).show();
      //noinspection deprecation
      email.setHintTextColor(getResources().getColor(R.color.reddish_pink));
      return true;
    }
    if (notHasText(password)) {
      Snackbar.make(getView(), R.string.password_field_required, Snackbar.LENGTH_SHORT).show();
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
  }

  @Override
  public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
    user.setBirthday(LocalDate.of(year, monthOfYear + 1, dayOfMonth));
    setUpBirthdayTextView();
  }

  private void setUpBirthdayTextView() {
    LocalDate date = user.getBirthday();
    String month = date.getMonth().getValue() <= 9 ? "0" + date.getMonth().getValue() : date.getMonth().getValue() + "";
    String day = date.getDayOfMonth() <= 9 ? "0" + date.getDayOfMonth() : date.getDayOfMonth() + "";
    String stringDate = month + "/" + day + "/" + date.getYear();
    birthday.setText(stringDate);
    //noinspection deprecation
    birthday.setTextColor(getResources().getColor(R.color.white));
  }
}
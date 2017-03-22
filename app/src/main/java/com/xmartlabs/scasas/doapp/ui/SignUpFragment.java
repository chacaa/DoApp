package com.xmartlabs.scasas.doapp.ui;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import android.view.View;

import android.widget.EditText;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.android.FragmentEvent;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import com.xmartlabs.scasas.doapp.Gender;
import com.xmartlabs.scasas.doapp.controller.GroupController;
import com.xmartlabs.scasas.doapp.controller.UserController;
import com.xmartlabs.scasas.doapp.model.Group;
import com.xmartlabs.scasas.doapp.model.User;
import com.xmartlabs.scasas.doapp.R;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.concurrent.CancellationException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.SingleSubscriber;
import timber.log.Timber;

/**
 * Created by santiago on 3/9/17.
 */
@FragmentWithArgs
public class SignUpFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener {
  @BindView(R.id.edit_text_user)
  EditText username;
  @BindView(R.id.edit_text_email)
  EditText email;
  @BindView(R.id.text_view_birthday)
  TextView birthday;
  @BindView(R.id.edit_text_password)
  EditText password;

  @Inject
  UserController userController;

  @Inject
  GroupController groupController;

  private User user = createEmptyUser();

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setUpBirthdayTextView();
  }

  @LayoutRes
  @Override
  protected int getLayoutResId() {
    return R.layout.singup_fragment;
  }

  @OnClick(R.id.sign_in)
  void onSingInClicked() {
    goToSinginActivity();
  }

  @OnClick(R.id.sign_up)
  void onSignUpClicked() {
    if (hasAnEmptyField()) {
      return;
    }
    setUserValues();
    insertUser();
  }

  @OnClick(R.id.text_view_birthday)
  void onBirthdayClicked() {
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
    userController.insertUser(user)
        .compose(RxLifecycle.<User, FragmentEvent>bindUntilEvent(lifecycle(), FragmentEvent.DESTROY_VIEW).forSingle())
        .subscribe(new SingleSubscriber<User>() {
          @Override
          public void onSuccess(User user) {
            createGroups();
            Intent intent = Henson.with(getContext())
                .gotoOnBoardingActivity()
                .user(user)
                .build();
            getContext().startActivity(intent);
          }

          @Override
          public void onError(Throwable error) {
            if (!(error instanceof CancellationException)) {
              //TODO
            }
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

  private boolean hasAnEmptyField() {
    if (theFieldIsEmpty(username)) {
      Snackbar.make(getView(), R.string.name_field_required, Snackbar.LENGTH_SHORT).show();
      //noinspection deprecation
      username.setHintTextColor(getResources().getColor(R.color.reddish_pink));
      return true;
    }
    if (theFieldIsEmpty(email)) {
      Snackbar.make(getView(), R.string.email_field_required, Snackbar.LENGTH_SHORT).show();
      //noinspection deprecation
      email.setHintTextColor(getResources().getColor(R.color.reddish_pink));
      return true;
    }
    if (theFieldIsEmpty(password)) {
      Snackbar.make(getView(), R.string.password_field_required, Snackbar.LENGTH_SHORT).show();
      //noinspection deprecation
      password.setHintTextColor(getResources().getColor(R.color.reddish_pink));
      return true;
    }
    return false;
  }

  private boolean theFieldIsEmpty(EditText editText) {
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
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    String stringDate = date.format(formatter);
    birthday.setText(stringDate);
    //noinspection deprecation
    birthday.setTextColor(getResources().getColor(R.color.white));
  }

  private void createGroups() {
    Stream.of(getResources().getStringArray(R.array.group_array))
        .forEach(stringResValue -> insertGroup(stringResValue));
  }

  private void insertGroup(String name) {
    Group group = Group.builder()
        .name(name)
        .user(user)
        .build();
    groupController.insertGroup(group).subscribe(new SingleSubscriber<Group>() {
      @Override
      public void onSuccess(Group value) {
      }

      @Override
      public void onError(Throwable error) {
        Snackbar.make(getView(),error.toString(),Snackbar.LENGTH_LONG);
        Timber.e(error);
      }
    });
  }
}

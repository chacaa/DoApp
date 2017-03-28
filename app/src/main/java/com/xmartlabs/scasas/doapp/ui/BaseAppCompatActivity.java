package com.xmartlabs.scasas.doapp.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.f2prateek.dart.Dart;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xmartlabs.scasas.doapp.DoAppApplication;

/**
 * Created by santiago on 31/08/15.
 */
public abstract class BaseAppCompatActivity extends RxAppCompatActivity {
  protected Context getContext() {
    return this;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Dart.inject(this);

    DoAppApplication.getContext().inject(this);
  }

  public void removeFragment(@NonNull Fragment fragment) {
    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
  }

  public void hideKeyboard() {
    InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
    View currentFocus = this.getCurrentFocus();
    if (currentFocus != null) {
      inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
    }
  }
}

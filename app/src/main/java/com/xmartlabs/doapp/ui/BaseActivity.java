package com.xmartlabs.doapp.ui;

import android.content.Context;
import android.os.Bundle;

import com.f2prateek.dart.Dart;
import com.trello.rxlifecycle.components.RxActivity;
import com.xmartlabs.doapp.DoAppApplication;

/**
 * Created by diegomedina24 on 12/16/16.
 */
public abstract class BaseActivity extends RxActivity {
  protected Context getContext() {
    return this;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Dart.inject(this);
    DoAppApplication.getContext().inject(this);
  }
}

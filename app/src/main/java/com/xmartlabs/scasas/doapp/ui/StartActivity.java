package com.xmartlabs.scasas.doapp.ui;

import android.content.Intent;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.xmartlabs.scasas.doapp.controller.AuthController;
import com.xmartlabs.scasas.doapp.controller.SessionController;
import com.xmartlabs.scasas.doapp.model.Session;

import io.fabric.sdk.android.Fabric;
import javax.inject.Inject;

/**
 * Created by santiago on 31/08/15.
 */
public class StartActivity extends BaseActivity {
  @Inject
  AuthController authController;
  @Inject
  SessionController sessionController;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Fabric.with(this, new Crashlytics());

    Session session = sessionController.getSession();

    Intent intent;
    if (session == null) {
      intent = Henson.with(getContext()).gotoSignInActivity().build();
    } else {
      intent = Henson.with(getContext()).gotoMainActivity().build();
      authController.setLoginInfo(session);
    }

    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    getContext().startActivity(intent);
  }
}

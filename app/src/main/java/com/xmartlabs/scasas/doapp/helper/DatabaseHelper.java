package com.xmartlabs.scasas.doapp.helper;

import android.support.annotation.NonNull;

import com.xmartlabs.scasas.doapp.DoAppApplication;
import com.xmartlabs.scasas.doapp.controller.SessionController;
import com.xmartlabs.scasas.doapp.model.Session;

import javax.inject.Inject;

/**
 * Created by santiago on 09/11/15.
 */
@SuppressWarnings("unused")
public class DatabaseHelper {
  @Inject
  SessionController sessionController;

  public DatabaseHelper() {
    DoAppApplication.getContext().inject(this);
  }

  public void deleteAll() {
    // TODO
  }

  public void migrate(@NonNull Session session) {
    if (session.getDatabaseVersion() == null || session.getDatabaseVersion() != Session.CURRENT_DATABASE_VERSION) { // Drop even if downgrading the version.
      deleteAll();
      session.setDatabaseVersion(Session.CURRENT_DATABASE_VERSION);
      sessionController.setSession(session).await();
    }
  }
}

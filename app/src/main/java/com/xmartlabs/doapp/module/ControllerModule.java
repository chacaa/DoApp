package com.xmartlabs.doapp.module;

import com.xmartlabs.doapp.controller.SessionController;
import com.xmartlabs.doapp.controller.AuthController;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by santiago on 31/08/15.
 */
@Module
public class ControllerModule {
  @Provides
  @Singleton
  AuthController provideAuthController() {
    return new AuthController();
  }

  @Provides
  @Singleton
  SessionController provideSessionController() {
    return new SessionController();
  }
}

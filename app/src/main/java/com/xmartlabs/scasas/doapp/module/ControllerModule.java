package com.xmartlabs.scasas.doapp.module;

import com.xmartlabs.scasas.doapp.controller.GroupController;
import com.xmartlabs.scasas.doapp.controller.SessionController;
import com.xmartlabs.scasas.doapp.controller.AuthController;
import com.xmartlabs.scasas.doapp.controller.TaskController;
import com.xmartlabs.scasas.doapp.controller.UserController;

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

  @Provides
  @Singleton
  UserController provideUserController() {
    return new UserController();
  }

  @Provides
  @Singleton
  TaskController provideTaskController() {
    return new TaskController();
  }

  @Provides
  @Singleton
  GroupController provideGroupController() {
    return new GroupController();
  }
}

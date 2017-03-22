package com.xmartlabs.scasas.doapp;

import com.xmartlabs.scasas.doapp.controller.Controller;
import com.xmartlabs.scasas.doapp.controller.ServiceController;
import com.xmartlabs.scasas.doapp.controller.SessionController;
import com.xmartlabs.scasas.doapp.helper.DatabaseHelper;
import com.xmartlabs.scasas.doapp.helper.GeneralErrorHelper;
import com.xmartlabs.scasas.doapp.model.Group;
import com.xmartlabs.scasas.doapp.module.AndroidModule;
import com.xmartlabs.scasas.doapp.module.ControllerModule;
import com.xmartlabs.scasas.doapp.module.GeneralErrorHelperModule;
import com.xmartlabs.scasas.doapp.module.OkHttpModule;
import com.xmartlabs.scasas.doapp.module.PicassoModule;
import com.xmartlabs.scasas.doapp.module.ReceiverModule;
import com.xmartlabs.scasas.doapp.module.RestServiceModule;
import com.xmartlabs.scasas.doapp.module.SessionInterceptor;
import com.xmartlabs.scasas.doapp.ui.BaseActivity;
import com.xmartlabs.scasas.doapp.ui.BaseAppCompatActivity;
import com.xmartlabs.scasas.doapp.ui.BaseFragment;
import com.xmartlabs.scasas.doapp.ui.FragmentWithDrawer;
import com.xmartlabs.scasas.doapp.ui.GroupsListActivity;
import com.xmartlabs.scasas.doapp.ui.GroupsListFragment;
import com.xmartlabs.scasas.doapp.ui.SignInActivity;
import com.xmartlabs.scasas.doapp.ui.SignInFragment;
import com.xmartlabs.scasas.doapp.ui.MainActivity;
import com.xmartlabs.scasas.doapp.ui.SignUpActivity;
import com.xmartlabs.scasas.doapp.ui.SignUpFragment;
import com.xmartlabs.scasas.doapp.ui.StartActivity;
import com.xmartlabs.scasas.doapp.ui.TasksListActivity;
import com.xmartlabs.scasas.doapp.ui.TasksListFragment;
import com.xmartlabs.scasas.doapp.ui.ValidatableFragment;
import com.xmartlabs.scasas.doapp.ui.WelcomeActivity;
import com.xmartlabs.scasas.doapp.ui.WelcomeFragment;
import com.xmartlabs.scasas.doapp.controller.AuthController;
import com.xmartlabs.scasas.doapp.module.DatabaseModule;
import com.xmartlabs.scasas.doapp.module.GsonModule;
import com.xmartlabs.scasas.doapp.ui.SingleFragmentActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by santiago on 06/10/15.
 */
@Singleton
@Component(modules = {
    AndroidModule.class,
    ControllerModule.class,
    DatabaseModule.class,
    GeneralErrorHelperModule.class,
    GsonModule.class,
    OkHttpModule.class,
    PicassoModule.class,
    ReceiverModule.class,
    RestServiceModule.class,
})
public interface ApplicationComponent {
  void inject(DoAppApplication doAppApplication);

  void inject(BaseActivity baseActivity);
  void inject(BaseAppCompatActivity baseAppCompatActivity);
  void inject(SingleFragmentActivity singleFragmentActivity);

  void inject(MainActivity mainActivity);
  void inject(SignInActivity signInActivity);
  void inject(SignUpActivity signUpActivity);
  void inject(StartActivity startActivity);
  void inject(TasksListActivity tasksListActivity);
  void inject(WelcomeActivity welcomeActivity);
  void inject(GroupsListActivity groupsListActivity);

  void inject(BaseFragment baseFragment);
  void inject(FragmentWithDrawer fragmentWithDrawer);
  void inject(ValidatableFragment validatableFragment);

  void inject(SignInFragment signInFragment);
  void inject(SignUpFragment signUpFragment);
  void inject(TasksListFragment tasksListFragment);
  void inject(WelcomeFragment welcomeFragment);
  void inject(GroupsListFragment groupsListFragment);

  void inject(Controller controller);
  void inject(ServiceController serviceController);

  void inject(AuthController authController);
  void inject(SessionController sessionController);

  void inject(SessionInterceptor sessionInterceptor);

  void inject(DatabaseHelper databaseHelper);
  void inject(GeneralErrorHelper generalErrorHelper);
}

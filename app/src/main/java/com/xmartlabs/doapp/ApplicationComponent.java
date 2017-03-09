package com.xmartlabs.doapp;

import com.xmartlabs.doapp.controller.Controller;
import com.xmartlabs.doapp.controller.ServiceController;
import com.xmartlabs.doapp.controller.SessionController;
import com.xmartlabs.doapp.helper.DatabaseHelper;
import com.xmartlabs.doapp.helper.GeneralErrorHelper;
import com.xmartlabs.doapp.module.AndroidModule;
import com.xmartlabs.doapp.module.ControllerModule;
import com.xmartlabs.doapp.module.GeneralErrorHelperModule;
import com.xmartlabs.doapp.module.OkHttpModule;
import com.xmartlabs.doapp.module.PicassoModule;
import com.xmartlabs.doapp.module.ReceiverModule;
import com.xmartlabs.doapp.module.RestServiceModule;
import com.xmartlabs.doapp.module.SessionInterceptor;
import com.xmartlabs.doapp.ui.BaseActivity;
import com.xmartlabs.doapp.ui.BaseAppCompatActivity;
import com.xmartlabs.doapp.ui.BaseFragment;
import com.xmartlabs.doapp.ui.FragmentWithDrawer;
import com.xmartlabs.doapp.ui.SinginActivity;
import com.xmartlabs.doapp.ui.SinginFragment;
import com.xmartlabs.doapp.ui.MainActivity;
import com.xmartlabs.doapp.ui.SingupActivity;
import com.xmartlabs.doapp.ui.SingupFragment;
import com.xmartlabs.doapp.ui.StartActivity;
import com.xmartlabs.doapp.ui.ValidatableFragment;
import com.xmartlabs.doapp.ui.WelcomeActivity;
import com.xmartlabs.doapp.ui.WelcomeFragment;
import com.xmartlabs.doapp.controller.AuthController;
import com.xmartlabs.doapp.module.DatabaseModule;
import com.xmartlabs.doapp.module.GsonModule;
import com.xmartlabs.doapp.ui.SingleFragmentActivity;

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
  void inject(StartActivity startActivity);
  void inject(WelcomeActivity welcomeActivity);

  void inject(FragmentWithDrawer fragmentWithDrawer);
  void inject(ValidatableFragment validatableFragment);

  void inject(WelcomeFragment welcomeFragment);

  void inject(Controller controller);
  void inject(ServiceController serviceController);

  void inject(AuthController authController);
  void inject(SessionController sessionController);

  void inject(SessionInterceptor sessionInterceptor);

  void inject(DatabaseHelper databaseHelper);
  void inject(GeneralErrorHelper generalErrorHelper);

  void inject(BaseFragment baseFragment);

  void inject(SinginFragment loginFragment);
  void inject(SinginActivity singinActivity);

  void inject(SingupFragment singupFragment);
  void inject(SingupActivity singupActivity);
}

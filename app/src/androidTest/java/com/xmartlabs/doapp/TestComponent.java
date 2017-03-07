package com.xmartlabs.doapp;

import com.xmartlabs.doapp.module.MockRestServiceModule;
import com.xmartlabs.doapp.ui.common.BaseInstrumentationTest;
import com.xmartlabs.doapp.module.AndroidModule;
import com.xmartlabs.doapp.module.ControllerModule;
import com.xmartlabs.doapp.module.DatabaseModule;
import com.xmartlabs.doapp.module.GeneralErrorHelperModule;
import com.xmartlabs.doapp.module.GsonModule;
import com.xmartlabs.doapp.module.OkHttpModule;
import com.xmartlabs.doapp.module.PicassoModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by medina on 21/09/2016.
 */
@Component(modules = {
    AndroidModule.class,
    ControllerModule.class,
    DatabaseModule.class,
    GeneralErrorHelperModule.class,
    GsonModule.class,
    MockRestServiceModule.class,
    OkHttpModule.class,
    PicassoModule.class,
})
@Singleton
public interface TestComponent extends ApplicationComponent {
  void inject(BaseInstrumentationTest baseInstrumentationTest);

  void inject(TestRunner testRunner);
}

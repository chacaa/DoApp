package com.xmartlabs.doapp;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.xmartlabs.doapp.helper.GeneralErrorHelper;
import com.xmartlabs.doapp.module.AndroidModule;
import com.xmartlabs.template.BuildConfig;

import javax.inject.Inject;

import bullet.ObjectGraph;
import io.fabric.sdk.android.Fabric;
import rx.plugins.RxJavaHooks;
import timber.log.Timber;

/**
 * Created by remer on 08/12/15.
 */
public class DoAppApplication extends Application {
  private static DoAppApplication instance;

  private ObjectGraph bullet;

  @Inject
  GeneralErrorHelper generalErrorHelper;

  public DoAppApplication() {
    instance = this;
  }

  public static DoAppApplication getContext() {
    return instance;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    initializeThreeTenABP();
    initializeInjections();
    initializeDataBase();
    initializeRxErrorHandler();
    initializeLogging(); // Crashlytics initialization should go at the end.
  }

  private void initializeInjections() {
    ApplicationComponent component = createComponent();
    bullet = createBullet(component);
    inject(this);
  }

  protected ApplicationComponent createComponent() {
    return DaggerApplicationComponent.builder()
        .androidModule(new AndroidModule(this))
        .build();
  }

  protected ObjectGraph createBullet(ApplicationComponent component) {
    return new BulletApplicationComponent(component);
  }

  private void initializeDataBase() {
    FlowManager.init(new FlowConfig.Builder(this).build());
  }

  private void initializeThreeTenABP() {
    AndroidThreeTen.init(this);
  }

  private void initializeLogging() {
    CrashlyticsCore crashlyticsCore = new CrashlyticsCore.Builder()
        .disabled(BuildConfig.DEBUG)
        .build();
    Crashlytics crashlytics = new Crashlytics.Builder().core(crashlyticsCore).build();
    Fabric.with(this, crashlytics);

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
    Timber.plant(new CrashlyticsTree());
  }

  public <T> T inject(final T t) {
    return bullet.inject(t);
  }

  private void initializeRxErrorHandler() {
    RxJavaHooks.setOnError(generalErrorHelper.getGeneralErrorAction());
  }
}

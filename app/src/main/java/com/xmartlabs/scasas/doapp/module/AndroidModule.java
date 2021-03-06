package com.xmartlabs.scasas.doapp.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.xmartlabs.scasas.doapp.DoAppApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by santiago on 17/09/15.
 */
@Module
public class AndroidModule {
  private final DoAppApplication application;

  public AndroidModule(DoAppApplication application) {
    this.application = application;
  }

  @Provides
  @Singleton
  Context provideApplicationContext() {
    return application;
  }

  @Provides
  @Singleton
  SharedPreferences provideSharedPreferences() {
    return PreferenceManager.getDefaultSharedPreferences(application);
  }
}

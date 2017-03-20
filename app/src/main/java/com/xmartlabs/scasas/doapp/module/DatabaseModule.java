package com.xmartlabs.scasas.doapp.module;

import com.xmartlabs.scasas.doapp.helper.DatabaseHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by santiago on 09/11/15.
 */
@Module
public class DatabaseModule {
  @Provides
  @Singleton
  DatabaseHelper provideDatabaseHelper() {
    return new DatabaseHelper();
  }
}

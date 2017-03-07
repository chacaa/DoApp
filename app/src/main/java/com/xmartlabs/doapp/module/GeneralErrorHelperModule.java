package com.xmartlabs.doapp.module;

import com.xmartlabs.doapp.helper.GeneralErrorHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by santiago on 12/10/15.
 */
@Module
public class GeneralErrorHelperModule {
  @Provides
  @Singleton
  GeneralErrorHelper provideGeneralErrorHelper() {
    return new GeneralErrorHelper();
  }
}

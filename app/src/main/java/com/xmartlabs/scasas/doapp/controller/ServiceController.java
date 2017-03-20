package com.xmartlabs.scasas.doapp.controller;

import com.xmartlabs.scasas.doapp.helper.GeneralErrorHelper;

import javax.inject.Inject;

/**
 * To distinguish between controllers that use the services, and those which don't.
 *
 * Created by santiago on 12/10/15.
 */
public abstract class ServiceController extends Controller {
  @Inject
  GeneralErrorHelper generalErrorHelper;

  public GeneralErrorHelper getGeneralErrorHelper() {
    return generalErrorHelper;
  }
}

package com.xmartlabs.scasas.doapp.controller;

import com.xmartlabs.scasas.doapp.DoAppApplication;

/**
 * Created by santiago on 17/09/15.
 */
public abstract class Controller {
  public Controller() {
    DoAppApplication.getContext().inject(this);
  }
}

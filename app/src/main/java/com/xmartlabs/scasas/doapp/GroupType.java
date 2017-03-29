package com.xmartlabs.scasas.doapp;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

/**
 * Created by scasas on 3/29/17.
 */
public enum GroupType {
  SHOP(R.string.shop, R.drawable.food_image),
  WORK(R.string.work, R.drawable.work),
  HEALTH(R.string.health, R.drawable.healthy),
  TRAVEL(R.string.travel, R.drawable.travels),
  BILLS(R.string.bills, R.drawable.bills),
  AUTO(R.string.auto, R.drawable.cars),;

  private final int title;
  private final int image;

  GroupType(@StringRes int title, @DrawableRes int image) {
    this.title = title;
    this.image = image;
  }

  public int getTitle() {
    return this.title;
  }

  public int getImage() {
    return this.image;
  }
}

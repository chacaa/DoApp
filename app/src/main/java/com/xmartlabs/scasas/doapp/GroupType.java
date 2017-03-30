package com.xmartlabs.scasas.doapp;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by scasas on 3/29/17.
 */
@Getter
@RequiredArgsConstructor
public enum GroupType {
  SHOP(R.string.shop, R.drawable.food_image),
  WORK(R.string.work, R.drawable.work),
  HEALTH(R.string.health, R.drawable.healthy),
  TRAVEL(R.string.travel, R.drawable.travels),
  BILLS(R.string.bills, R.drawable.bills),
  AUTO(R.string.auto, R.drawable.cars),;

  private final int title;
  private final int image;
}

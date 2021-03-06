package com.xmartlabs.scasas.doapp.helper.ui;

import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;

import com.xmartlabs.scasas.doapp.DoAppApplication;

/**
 * Created by medina on 19/09/2016.
 */
@SuppressWarnings("unused")
public class ColorHelper {
  /**
   * Retrieves the Hex string representation of the color resource
   * @param colorResId the id of the color resource
   * @return the Hex string representation of the color resource
   */
  @NonNull
  public static String getResourceColorHexString(@ColorRes int colorResId) {
    //noinspection deprecation
    int color = DoAppApplication.getContext().getResources().getColor(colorResId);
    return String.format("#%06X", (0xFFFFFF & color));
  }
}

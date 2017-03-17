package com.xmartlabs.doapp.ui;

import android.support.annotation.Nullable;

/**
 * Created by santiago on 06/10/15.
 */
public abstract class FragmentWithDrawer extends ValidatableFragment {
  /**
   * This may be called when fragment is not attached, so be careful with context.
   *
   * @return The fragment titleView
   */
  @Nullable
  public String getTitle() {
    return null;
  }

  public boolean hasTabLayout() {
    return false;
  }

  @SuppressWarnings("unused")
  public boolean scrollToolbar() {
    return hasTabLayout();
  }
}

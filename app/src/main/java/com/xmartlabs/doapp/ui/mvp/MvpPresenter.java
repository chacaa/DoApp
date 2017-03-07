package com.xmartlabs.template.ui.mvp;

import android.support.annotation.UiThread;

/**
 * The base interface that defines a presenter in the MVP patterns.
 *
 * @param <V> The view type to be attached to this presenter
 */
public interface MvpPresenter<V extends MvpView> {
  /**
   * Sets the view to this presenter instance.
   *
   * @param view the view to be attached to this presenter
   */
  @UiThread
  void attachView(V view);

  /**
   * This method will be called when the view is destroyed.
   * If the view is an activity, it will be invoked from <code>Activity.onDestroy()</code>.
   * If the view is a fragment, it will be invoked from <code>Fragment.onDestroyView()</code>.
   */
  @UiThread
  void detachView();
}

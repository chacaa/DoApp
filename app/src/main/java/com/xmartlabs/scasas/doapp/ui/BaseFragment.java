package com.xmartlabs.scasas.doapp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxFragment;
import com.xmartlabs.scasas.doapp.DoAppApplication;
import com.xmartlabs.scasas.doapp.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by santiago on 15/09/15.
 */
@FragmentWithArgs
public abstract class BaseFragment extends RxFragment {
  private Unbinder unbinder;
  @Nullable
  private ProgressDialog progressDialog;

  @LayoutRes
  protected abstract int getLayoutResId();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    FragmentArgs.inject(this);
    DoAppApplication.getContext().inject(this);
  }

  @NonNull
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(getLayoutResId(), container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    progressDialog = new ProgressDialog(activity);
    progressDialog.setCancelable(false);
    progressDialog.setMessage(getString(R.string.loading));
  }

  @Override
  public void onDetach() {
    super.onDetach();
    if (progressDialog != null) {
      progressDialog.dismiss();
    }
    progressDialog = null;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  protected void showAlertError(int stringResId) {
    new AlertDialog.Builder(getContext())
        .setMessage(stringResId)
        .setNeutralButton(android.R.string.ok, null)
        .show();
  }

  public void showProgressDialog() {
    if (progressDialog != null) {
      progressDialog.show();
    }
  }

  public void hideProgressDialog() {
    if (progressDialog != null) {
      progressDialog.hide();
    }
  }

  protected void removeItselfFromActivity() {
    ((BaseAppCompatActivity) getActivity()).removeFragment(this);
  }

  protected void removeItselfFromParentFragment() {
    getParentFragment().getChildFragmentManager().beginTransaction().remove(this).commit();
  }

  protected void removeItselfFromParent() {
    if (getParentFragment() == null) {
      removeItselfFromActivity();
    } else {
      removeItselfFromParentFragment();
    }
  }

  protected void showSnackbarMessage(@StringRes int stringId) {
    if (getView() != null) {
      Snackbar.make(getView(), stringId, Snackbar.LENGTH_SHORT).show();
    }
  }

  public void hideKeyboard() {
    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
    View currentFocus = getActivity().getCurrentFocus();
    if (currentFocus != null) {
      inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
    }
  }
}

package com.xmartlabs.doapp.controller;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import com.xmartlabs.doapp.model.User;
import com.xmartlabs.doapp.model.User_Table;

import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by scasas on 3/9/17.
 */
public class UserController extends Controller {
  @NonNull
  private final static UserController INSTANCE = new UserController();

  public static UserController getInstance() {
    return INSTANCE;
  }

  public Single<User> insertUser(User user) {
    return Single
        .fromCallable(() -> {
          user.insert();
          return user;
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io());
  }

  public Single<User> getUser(String email) {
    return Single
        .fromCallable(() -> SQLite.select()
            .from(User.class)
            .where(User_Table.email.is(email))
            .queryResults()
            .toModel()
        )
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io());
  }

}

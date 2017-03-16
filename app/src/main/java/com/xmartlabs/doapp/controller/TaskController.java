package com.xmartlabs.doapp.controller;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.xmartlabs.doapp.model.Task;

import java.util.List;

import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static rx.Single.fromCallable;

/**
 * Created by scasas on 3/16/17.
 */

public class TaskController extends Controller {
  public Single<Task> insertTask(Task task) {
    return
        fromCallable(() -> {
          task.insert();
          return task;
        })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io());
  }

  public Single<List<Task>> getTasks() {
    return Single
        .fromCallable(() -> SQLite.select()
            .from(Task.class)
            .where()
            .queryResults()
            .toList()
        )
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io());
  }

  public Single<Task> changeTaskState(Task task) {
    return
        fromCallable(() -> {
          task.setFinished(!task.isFinished());
          task.update();
          return task;
        })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io());
  }
}

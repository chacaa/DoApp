package com.xmartlabs.scasas.doapp.controller;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.xmartlabs.scasas.doapp.model.Group;
import com.xmartlabs.scasas.doapp.model.Task;
import com.xmartlabs.scasas.doapp.model.Task_Table;
import com.xmartlabs.scasas.doapp.model.User;
import com.xmartlabs.scasas.doapp.ui.SingleFragmentActivity;

import java.util.List;

import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by scasas on 3/16/17.
 */
public class TaskController extends Controller {
  public Single<Task> insertTask(Task task) {
    return Single
        .fromCallable(() -> {
          task.insert();
          return task;
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io());
  }

  public Single<List<Task>> getTasks(User user, Group group) {
    return Single
        .fromCallable(() -> SQLite.select()
            .from(Task.class)
            .where(Task_Table.user_id.eq(user.getId()))
            .and(Task_Table.group_id.eq(group.getId()))
            .queryResults()
            .toList()
        )
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io());
  }

  public Single<Task> changeTaskState(Task task) {
    return Single
        .fromCallable(() -> {
          task.setFinished(!task.isFinished());
          task.update();
          return task;
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io());
  }

  public Single<Long> getTasksCount(User user, Group group) {
    return Single
        .fromCallable(() -> SQLite.select()
            .from(Task.class)
            .where(Task_Table.user_id.eq(user.getId()))
            .and(Task_Table.group_id.eq(group.getId()))
            .count()
        )
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io());
  }
}

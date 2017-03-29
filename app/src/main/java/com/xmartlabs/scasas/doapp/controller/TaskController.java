package com.xmartlabs.scasas.doapp.controller;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.xmartlabs.scasas.doapp.model.Group;
import com.xmartlabs.scasas.doapp.model.Task;
import com.xmartlabs.scasas.doapp.model.Task_Table;
import com.xmartlabs.scasas.doapp.model.User;

import org.threeten.bp.LocalDate;

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
            .queryList()
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
        .fromCallable(() -> SQLite.selectCountOf()
            .from(Task.class)
            .where(Task_Table.user_id.eq(user.getId()))
            .and(Task_Table.group_id.eq(group.getId()))
            .count()
        )
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io());
  }

  public Single<Long> getTasksCountForASpecificMonth(User user, LocalDate date) {
    return Single
        .fromCallable(() -> SQLite.selectCountOf()
            .from(Task.class)
            .where(Task_Table.user_id.eq(user.getId()))
            .and(Task_Table.date.greaterThanOrEq(date))
            .and(Task_Table.date.lessThan(date.plusMonths(1)))
            .count()
        )
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io());
  }

  public Single<Long> getFinishedTasksCount(User user, Group group) {
    return Single
        .fromCallable(() -> SQLite.selectCountOf()
            .from(Task.class)
            .where(Task_Table.user_id.eq(user.getId()))
            .and(Task_Table.group_id.eq(group.getId()))
            .and(Task_Table.isFinished.eq(true))
            .count()
        )
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io());
  }

  public Single<Long> getFinishedTasksCountForASpecificMonth(User user, LocalDate date) {
    return Single
        .fromCallable(() -> SQLite.selectCountOf()
            .from(Task.class)
            .where(Task_Table.user_id.eq(user.getId()))
            .and(Task_Table.date.greaterThanOrEq(date))
            .and(Task_Table.date.lessThan(date.plusMonths(1)))
            .and(Task_Table.isFinished.eq(true))
            .count()
        )
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io());
  }

  public Single<Long> getFinishPercentage(User user, LocalDate date) {
    return Single
        .zip(getTasksCountForASpecificMonth(user, date),
            getFinishedTasksCountForASpecificMonth(user, date),
            (totalCount, finishedCount) -> totalCount == 0 ? 0L : finishedCount * 100 / totalCount
        )
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io());
  }
}

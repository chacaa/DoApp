package com.xmartlabs.scasas.doapp.controller;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.xmartlabs.scasas.doapp.model.Group;
import com.xmartlabs.scasas.doapp.model.Group_Table;
import com.xmartlabs.scasas.doapp.model.User;

import java.util.List;

import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by santiago on 3/21/17.
 */
public class GroupController extends Controller {
    public Single<Group> insertGroup(Group group) {
        return Single
                .fromCallable(() -> {
                    group.insert();
                    return group;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Single<List<Group>> getGroups(User user) {
        return Single
                .fromCallable(() -> SQLite.select()
                        .from(Group.class)
                        .where(Group_Table.user_id.eq(user.getId()))
                        .queryResults()
                        .toList()
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Single<Group> updateGroup(Group group) {
        return Single
                .fromCallable(() -> {
                    group.update();
                    return group;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}

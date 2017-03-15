package com.xmartlabs.doapp.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by mirland on 20/04/16.
 */
@Database(name = AppDataBase.NAME, version = AppDataBase.VERSION)
public class AppDataBase {
  public static final String NAME = "doapp_database";
  public static final int VERSION = 3;
}

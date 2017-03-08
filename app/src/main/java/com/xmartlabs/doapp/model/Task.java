package com.xmartlabs.doapp.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.xmartlabs.doapp.database.AppDataBase;

import org.parceler.Parcel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by scasas on 3/7/17.
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@Parcel
@Table(database = AppDataBase.class, cachingEnabled = true, cacheSize = 100)
public class Task extends BaseModel {
  @PrimaryKey(autoincrement = true)
  long id;
  @Column
  String name;
  @Column
  int date;
  @Column
  Double duration;
  @Column
  boolean isFinished;
  @ForeignKey
  User user;
  @ForeignKey
  Group group;
}

package com.xmartlabs.scasas.doapp.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.xmartlabs.scasas.doapp.LocalDateAdapter;
import com.xmartlabs.scasas.doapp.database.AppDataBase;

import org.parceler.Parcel;
import org.threeten.bp.LocalDate;

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
  String title;
  @Column
  String description;
  @Column(typeConverter = LocalDateAdapter.class)
  LocalDate date;
  @Column
  boolean isFinished;
  //TODO: I'll be using it later in the next feature
//  @ForeignKey
//  User user;
//  @ForeignKey
//  Group group;
}

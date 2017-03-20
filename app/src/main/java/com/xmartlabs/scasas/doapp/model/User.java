package com.xmartlabs.scasas.doapp.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.xmartlabs.scasas.doapp.Gender;
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
public class User extends BaseModel {
  @PrimaryKey(autoincrement = true)
  long id;
  @Column
  String name;
  @Column
  Gender gender;
  @Column
  @Unique
  String email;
  @Column(typeConverter = LocalDateAdapter.class)
  LocalDate birthday;
  @Column
  String password;
}

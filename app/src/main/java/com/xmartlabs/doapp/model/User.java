package com.xmartlabs.doapp.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.xmartlabs.doapp.Gender;
import com.xmartlabs.doapp.database.AppDataBase;

import org.parceler.Parcel;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by scasas on 3/7/17.
 */
@AllArgsConstructor
@Data
@Builder
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
  String email;
  @Column
  Date date;
  @Column
  String password;
}

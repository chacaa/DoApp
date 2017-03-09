package com.xmartlabs.doapp.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.TypeConverter;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.xmartlabs.doapp.Gender;
import com.xmartlabs.doapp.MilisecondsLocalDateAdapter;
import com.xmartlabs.doapp.database.AppDataBase;

import org.parceler.Parcel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
  String email;
  @Column(typeConverter = MilisecondsLocalDateAdapter.class)
  long birthday;
  @Column
  String password;
}

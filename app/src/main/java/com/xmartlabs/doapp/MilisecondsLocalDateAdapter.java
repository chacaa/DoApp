package com.xmartlabs.doapp;

import com.raizlabs.android.dbflow.converter.TypeConverter;

import org.threeten.bp.LocalDate;

/**
 * Created by scasas on 3/9/17.
 */
@com.raizlabs.android.dbflow.annotation.TypeConverter
public class MilisecondsLocalDateAdapter extends TypeConverter<Long, LocalDate> {
  @Override
  public Long getDBValue(LocalDate model) {
    return model == null ? null : model.toEpochDay();
  }

  @Override
  public LocalDate getModelValue(Long data) {
    return data == null ? null : LocalDate.ofEpochDay(data);
  }
}

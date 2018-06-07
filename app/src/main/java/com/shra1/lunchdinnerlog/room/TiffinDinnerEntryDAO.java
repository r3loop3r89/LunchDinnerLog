package com.shra1.lunchdinnerlog.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.shra1.lunchdinnerlog.dtos.TiffinDinnerEntry;

@Dao
public interface TiffinDinnerEntryDAO {

    @Insert
    public long addTiffinDinnerEntry(TiffinDinnerEntry tiffinDinnerEntry);

}

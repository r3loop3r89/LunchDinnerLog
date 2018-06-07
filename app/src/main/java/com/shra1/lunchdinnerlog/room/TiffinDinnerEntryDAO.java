package com.shra1.lunchdinnerlog.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.shra1.lunchdinnerlog.dtos.TiffinDinnerEntry;

import java.util.List;

@Dao
public interface TiffinDinnerEntryDAO {

    @Insert
    public long addTiffinDinnerEntry(TiffinDinnerEntry tiffinDinnerEntry);

    @Query("SELECT * FROM tiffindinnerentry WHERE EntryOnEpoch >= :frx AND EntryOnEpoch<=:tox")
    List<TiffinDinnerEntry> getTiffinDinnerEntriesForDate(long frx, long tox);
}

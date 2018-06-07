package com.shra1.lunchdinnerlog.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.shra1.lunchdinnerlog.App;
import com.shra1.lunchdinnerlog.dtos.Person;
import com.shra1.lunchdinnerlog.dtos.TiffinDinnerEntry;

@Database(entities = {Person.class, TiffinDinnerEntry.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static AppDatabase INSTANCE = null;

    public static AppDatabase getInstance(Context mCtx) {

        if (INSTANCE==null){
            INSTANCE= Room.databaseBuilder(
                    mCtx,
                    AppDatabase.class,
                    "Shra1db.db"
            ).build();
        }
        return INSTANCE;
    }

    public abstract PersonDAO getPersonDao();
    public abstract TiffinDinnerEntryDAO getTiffinDinnerEntryDao();

}

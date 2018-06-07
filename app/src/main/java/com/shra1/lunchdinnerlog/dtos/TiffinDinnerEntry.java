package com.shra1.lunchdinnerlog.dtos;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.AsyncTask;

import com.shra1.lunchdinnerlog.room.AppDatabase;

@Entity
public class TiffinDinnerEntry
{
    @PrimaryKey(autoGenerate = true)
    long Id;
    int PersonId;
    long EntryOnEpoch;
    String EntryType;//TIFFIN or DINNER

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public int getPersonId() {
        return PersonId;
    }

    public void setPersonId(int personId) {
        PersonId = personId;
    }

    public long getEntryOnEpoch() {
        return EntryOnEpoch;
    }

    public void setEntryOnEpoch(long entryOnEpoch) {
        EntryOnEpoch = entryOnEpoch;
    }

    public String getEntryType() {
        return EntryType;
    }

    public void setEntryType(String entryType) {
        EntryType = entryType;
    }

    public TiffinDinnerEntry() {

    }

    public TiffinDinnerEntry(int personId, long entryOnEpoch, String entryType) {

        PersonId = personId;
        EntryOnEpoch = entryOnEpoch;
        EntryType = entryType;
    }

    public static class DBCommands {
        public static void addTiffinEntry(AppDatabase adb, TiffinDinnerEntry tiffinDinnerEntry, AddTiffinEntryCallback c) {
            AddTiffinEntryTask addTiffinEntryTask = new AddTiffinEntryTask(c);
            addTiffinEntryTask.execute(adb, tiffinDinnerEntry);
        }
        static class AddTiffinEntryTask extends AsyncTask<Object, Void, Long>{
            AddTiffinEntryCallback c;
            public AddTiffinEntryTask(AddTiffinEntryCallback c) {
                this.c=c;
            }

            @Override
            protected Long doInBackground(Object... objects) {
                AppDatabase adb = (AppDatabase) objects[0];
                TiffinDinnerEntry e = (TiffinDinnerEntry) objects[1];
                return adb.getTiffinDinnerEntryDao().addTiffinDinnerEntry(e);
            }

            @Override
            protected void onPostExecute(Long aLong) {
                super.onPostExecute(aLong);
                c.onComplete(aLong);
            }
        }
        public interface AddTiffinEntryCallback{
            public void onComplete(long id);
        }
    }
}

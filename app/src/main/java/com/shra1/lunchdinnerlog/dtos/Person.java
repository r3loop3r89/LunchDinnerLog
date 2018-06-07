package com.shra1.lunchdinnerlog.dtos;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.AsyncTask;

import com.shra1.lunchdinnerlog.room.AppDatabase;

import org.joda.time.DateTime;

import java.util.List;

@Entity
public class Person {
    @PrimaryKey(autoGenerate = true)
    int Id;
    String FullName;
    String Address;
    String Phone;
    long AddedOnEpoch;
    @Ignore
    boolean isSelectedDaysTiffinEntryDone;

    @Ignore
    boolean isSelectedDaysDinnerEntryDone;

    public Person() {
    }

    public Person(String fullName, String address, String phone, long addedOnEpoch) {

        FullName = fullName;
        Address = address;
        Phone = phone;
        AddedOnEpoch = addedOnEpoch;
    }

    public boolean isSelectedDaysTiffinEntryDone() {
        return isSelectedDaysTiffinEntryDone;
    }

    public void setSelectedDaysTiffinEntryDone(boolean selectedDaysTiffinEntryDone) {
        isSelectedDaysTiffinEntryDone = selectedDaysTiffinEntryDone;
    }

    public boolean isSelectedDaysDinnerEntryDone() {
        return isSelectedDaysDinnerEntryDone;
    }

    public void setSelectedDaysDinnerEntryDone(boolean selectedDaysDinnerEntryDone) {
        isSelectedDaysDinnerEntryDone = selectedDaysDinnerEntryDone;
    }

    public int getId() {

        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public long getAddedOnEpoch() {
        return AddedOnEpoch;
    }

    public void setAddedOnEpoch(long addedOnEpoch) {
        AddedOnEpoch = addedOnEpoch;
    }

    public static class DBCommands {
        public static void addPerson(AppDatabase adb, Person person, AddPersonTaskCallback c) {
            AddPersonTask addPersonTask = new AddPersonTask(c);
            addPersonTask.execute(adb, person);
        }

        public static void getPeopleData(AppDatabase adb, DateTime dt, GetPeopleDataTaskCallback c) {

            TiffinDinnerEntry.DBCommands.getTiffinDinnerEntriesForDate(adb, dt, tiffinEntries -> {
                GetPeopleDataTask getPeopleDataTask = new GetPeopleDataTask(c, tiffinEntries);
                getPeopleDataTask.execute(adb);
            });


        }

        public interface GetPeopleDataTaskCallback {
            public void onCompleted(List<Person> people);
        }

        public interface AddPersonTaskCallback {
            public void onComplete(long l);
        }

        static class GetPeopleDataTask extends AsyncTask<Object, Void, List<Person>> {
            GetPeopleDataTaskCallback c;
            List<TiffinDinnerEntry> tiffinEntries;

            public GetPeopleDataTask(GetPeopleDataTaskCallback c, List<TiffinDinnerEntry> tiffinEntries) {
                this.c = c;
                this.tiffinEntries = tiffinEntries;
            }

            @Override
            protected List<Person> doInBackground(Object... objects) {
                AppDatabase adb = (AppDatabase) objects[0];
                List<Person> people = adb.getPersonDao().getPeopleData();

                for (TiffinDinnerEntry tde:tiffinEntries){
                    for (Person p: people){
                        if (p.Id==tde.PersonId){
                            if (tde.EntryType.equals("TIFFIN")){
                                p.setSelectedDaysTiffinEntryDone(true);
                            }else{
                                p.setSelectedDaysTiffinEntryDone(false);
                            }
                            if (tde.EntryType.equals("DINNER")){
                                p.setSelectedDaysDinnerEntryDone(true);
                            }else {
                                p.setSelectedDaysDinnerEntryDone(false);
                            }
                        }
                    }
                }

                return people;
            }

            @Override
            protected void onPostExecute(List<Person> people) {
                super.onPostExecute(people);
                c.onCompleted(people);
            }
        }

        static class AddPersonTask extends AsyncTask<Object, Void, Long> {
            AddPersonTaskCallback c;

            public AddPersonTask(AddPersonTaskCallback c) {
                this.c = c;
            }

            @Override
            protected Long doInBackground(Object... objects) {
                AppDatabase adb = (AppDatabase) objects[0];
                Person person = (Person) objects[1];
                return adb.getPersonDao().addPerson(person);
            }

            @Override
            protected void onPostExecute(Long aLong) {
                super.onPostExecute(aLong);
                c.onComplete(aLong);
            }
        }
    }
}

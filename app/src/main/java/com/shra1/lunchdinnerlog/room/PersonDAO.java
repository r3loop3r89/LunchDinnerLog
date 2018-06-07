package com.shra1.lunchdinnerlog.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.shra1.lunchdinnerlog.dtos.Person;

import java.util.List;

@Dao
public interface PersonDAO {

    @Insert
    public long addPerson(Person person);

    @Query("Select * from Person")
    public List<Person> getPeopleData();

}

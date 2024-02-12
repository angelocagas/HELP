package com.example.projectone.Databases;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ProjectTable.class},version = 1)

public abstract class ProjectDatabase extends RoomDatabase {
    public abstract ProjectDAO projectDAO();
}

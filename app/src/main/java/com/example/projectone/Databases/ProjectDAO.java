package com.example.projectone.Databases;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao

public interface ProjectDAO {
    //For Inserting data
    @Insert
    void insertData(ProjectTable projectTable);
    //for getting all the data
    @Query("SELECT * FROM projecttable")
    List<ProjectTable> selectAll();

    @Update
    void updateData(ProjectTable projectTable);


}

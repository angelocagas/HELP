package com.example.projectone.Databases;

import androidx.room.Dao;
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
    @Query("SELECT * FROM projecttable")
    List<ProjectTable> selectITEM();
    @Query("SELECT * FROM projecttable")
    List<ProjectTable> selectAT();
    @Query("SELECT * FROM projecttable")
    List<ProjectTable> selectA();

    @Update
    void updateData(ProjectTable projectTable);


    // Add this method to delete all projects from the table
    @Query("DELETE FROM projecttable")
    void deleteAllProjects();




}

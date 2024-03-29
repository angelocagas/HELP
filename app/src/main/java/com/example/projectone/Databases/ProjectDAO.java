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

    @Query("SELECT * FROM projecttable")
    List<ProjectTable> selectVA();

    @Update
    void updateData(ProjectTable projectTable);

    // For getting all items
    @Query("SELECT DISTINCT Item FROM projecttable")
    List<String> selectDistinctItems();

    // For getting A values for items starting with "ACU" or "Refrigerator"
    @Query("SELECT A FROM projecttable WHERE Item LIKE 'ACU%' OR Item = 'Refrigerator'")
    List<String> selectAForACURefrigerator();

    // For getting A values for items starting with "lighting outlet"
    @Query("SELECT A FROM projecttable WHERE Item LIKE 'Lighting Outlet%'")
    List<String> selectAForLightingOutlet();


    // Add this method to delete all projects from the table
    @Query("DELETE FROM projecttable")
    void deleteAllProjects();




}

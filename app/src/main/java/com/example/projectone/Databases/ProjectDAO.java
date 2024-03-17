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

    @Query("SELECT A FROM projecttable")
    List<String> getA();
    @Query("SELECT VA FROM projecttable")
    List<String> getVA();

    @Query("SELECT A, Item FROM ProjectTable")
    List<ProjectTableProjection> getAAndItem();

     class ProjectTableProjection {
        public String A;
        public String Item;

        public ProjectTableProjection(String A, String Item) {
            this.A = A;
            this.Item = Item;
        }
    }

    @Update
    void updateData(ProjectTable projectTable);

    // Add this method to delete all projects from the table
    @Query("DELETE FROM projecttable")
    void deleteAllProjects();


}

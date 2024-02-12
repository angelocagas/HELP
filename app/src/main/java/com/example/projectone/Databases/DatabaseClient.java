package com.example.projectone.Databases;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {

    Context context;
    static DatabaseClient client;
    ProjectDatabase projectDatabase;

    public DatabaseClient(Context context)
    {
        this.context = context;

        projectDatabase = Room.databaseBuilder(context,ProjectDatabase.class,"ProjectDatabase").build();
    }

    public static synchronized DatabaseClient getInstance(Context context)
    {
    if (client == null)
    {
        client = new DatabaseClient(context);
    }
    return client;
    }

    public ProjectDatabase getProjectDatabase()
    {
        return projectDatabase;
    }

}

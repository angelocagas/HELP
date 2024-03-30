package com.example.projectone.Helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import com.example.projectone.Databases.DatabaseClient;
import com.example.projectone.Databases.ProjectTable;
import com.example.projectone.Loadschedule;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    Context context;
    public interface OnItemsLoadedListener {
        void onItemsLoaded(List<String> items);
    }
    public DatabaseHelper (Context context)
    {
        this.context = context;
    }
    public  static  DatabaseHelper getInstance(Context context)
    {
        return new DatabaseHelper(context);
    }

    //addnew projecttable
    public void addNewProject(String ProjectName, String Quantity, String Item, String OPlus, String V, String VA, String A, String P, String AT, String AF, String SNUM, String SMM, String STYPE, String GNUM, String GMM, String GTYPE, String MMPlus, String CTYPE)
    {
        class NewProject extends AsyncTask<Void, Void, ProjectTable>
        {


            @Override
            protected ProjectTable doInBackground(Void... voids) {
                ProjectTable projectTable = new ProjectTable();
                projectTable.setProjectName(ProjectName);
                projectTable.setQuantity(Quantity);
                projectTable.setItem(Item);
                projectTable.setOPlus(OPlus);
                projectTable.setV(V);
                projectTable.setVA(VA);
                projectTable.setA(A);
                projectTable.setP(P);
                projectTable.setAT(AT);
                projectTable.setAF(AF);
                projectTable.setSNUM(SNUM);
                projectTable.setSMM(SMM);
                projectTable.setSTYPE(STYPE);
                projectTable.setGNUM(GNUM);
                projectTable.setGMM(GMM);
                projectTable.setGTYPE(GTYPE);
                projectTable.setMMPlus(MMPlus);
                projectTable.setCTYPE(CTYPE);

                DatabaseClient.getInstance(context)
                        .getProjectDatabase()
                        .projectDAO()
                        .insertData(projectTable);

                return null;
            }

            @Override
            protected void onPostExecute(ProjectTable projectTable) {
                super.onPostExecute(projectTable);
                if (projectTable != null)
                {
                 //Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
                }
            }
        }

        NewProject newProject = new NewProject();
        newProject.execute();

    }



    //getting all data of project
    public void getAllProjectData()
    {
        class AllProject extends AsyncTask<Void,Void,List<ProjectTable>>
        {

            @Override
            protected List<ProjectTable> doInBackground(Void... voids) {
                List<ProjectTable> list = DatabaseClient.getInstance(context)
                        .getProjectDatabase()
                        .projectDAO()
                        .selectAll();
                return list;
            }

            @Override
            protected void onPostExecute(List<ProjectTable> projectTables) {
                super.onPostExecute(projectTables);
                if (projectTables != null && projectTables.size() >0 )
                {
                    ((Loadschedule)context).setRecyclerView(projectTables);
                }

            }
        }
        AllProject allProject = new AllProject();
        allProject.execute();
    }






    public void getAllItemsAndStartNextActivity(final Context context, final OnItemsLoadedListener listener) {
        class GetAllItemsTask extends AsyncTask<Void, Void, List<String>> {
            @Override
            protected List<String> doInBackground(Void... voids) {
                // Retrieve all items from the ProjectTable
                List<ProjectTable> projectTables = DatabaseClient.getInstance(context)
                        .getProjectDatabase()
                        .projectDAO()
                        .selectITEM();
                List<String> itemList = new ArrayList<>();
                for (ProjectTable projectTable : projectTables) {
                    // Add each item to the itemList
                    itemList.add(projectTable.getItem());
                }
                return itemList;
            }

            @Override
            protected void onPostExecute(List<String> itemList) {
                super.onPostExecute(itemList);
                if (itemList != null && !itemList.isEmpty()) {
                    // Notify the listener with the list of items
                    listener.onItemsLoaded(itemList);
                } else {
                 //Toast.makeText(context, "No items found", Toast.LENGTH_SHORT).show();
                }
            }
        }

        GetAllItemsTask getAllItemsTask = new GetAllItemsTask();
        getAllItemsTask.execute();
    }
    /*public void getAForLightingOutlet(final Context context, final OnItemsLoadedListener listener) {
        class GetAForLightingOutletTask extends AsyncTask<Void, Void, List<String>> {
            @Override
            protected List<String> doInBackground(Void... voids) {
                return DatabaseClient.getInstance(context)
                        .getProjectDatabase()
                        .projectDAO()
                        .selectAForLightingOutlet();
            }

            @Override
            protected void onPostExecute(List<String> aList) {
                super.onPostExecute(aList);
                if (aList != null && !aList.isEmpty()) {
                    listener.onItemsLoaded(aList);
                } else {
                    Toast.makeText(context, "No A values found for lighting outlet", Toast.LENGTH_SHORT).show();
                }
            }
        }

        GetAForLightingOutletTask getAForLightingOutletTask = new GetAForLightingOutletTask();
        getAForLightingOutletTask.execute();
    }
    public void getAForACURefrigerator(final Context context, final OnItemsLoadedListener listener) {
        class GetAForACURefrigeratorTask extends AsyncTask<Void, Void, List<String>> {
            @Override
            protected List<String> doInBackground(Void... voids) {
                return DatabaseClient.getInstance(context)
                        .getProjectDatabase()
                        .projectDAO()
                        .selectAForACURefrigerator();
            }

            @Override
            protected void onPostExecute(List<String> aList) {
                super.onPostExecute(aList);
                if (aList != null && !aList.isEmpty()) {
                    listener.onItemsLoaded(aList);
                } else {
                    Toast.makeText(context, "No A values found for ACU/Refrigerator", Toast.LENGTH_SHORT).show();
                }
            }
        }

        GetAForACURefrigeratorTask getAForACURefrigeratorTask = new GetAForACURefrigeratorTask();
        getAForACURefrigeratorTask.execute();
    }*/


    public void getAForACURefrigerator(final Context context, final OnItemsLoadedListener listener) {
        class GetAForACURefrigeratorTask extends AsyncTask<Void, Void, List<String>> {
            @Override
            protected List<String> doInBackground(Void... voids) {
                return DatabaseClient.getInstance(context)
                        .getProjectDatabase()
                        .projectDAO()
                        .selectAForACURefrigerator();
            }

            @Override
            protected void onPostExecute(List<String> aList) {
                super.onPostExecute(aList);
                if (aList != null && !aList.isEmpty()) {
                    listener.onItemsLoaded(aList);
                } else {
                    getAForLightingOutlet(context, listener);
                }
            }
        }

        GetAForACURefrigeratorTask getAForACURefrigeratorTask = new GetAForACURefrigeratorTask();
        getAForACURefrigeratorTask.execute();
    }

    public void getAForLightingOutlet(final Context context, final OnItemsLoadedListener listener) {
        class GetAForLightingOutletTask extends AsyncTask<Void, Void, List<String>> {
            @Override
            protected List<String> doInBackground(Void... voids) {
                return DatabaseClient.getInstance(context)
                        .getProjectDatabase()
                        .projectDAO()
                        .selectAForLightingOutlet();
            }

            @Override
            protected void onPostExecute(List<String> aList) {
                super.onPostExecute(aList);
                if (aList != null && !aList.isEmpty()) {
                    listener.onItemsLoaded(aList);
                } else {
                  //  Toast.makeText(context, "No A values found for lighting outlet", Toast.LENGTH_SHORT).show();
                }
            }
        }

        GetAForLightingOutletTask getAForLightingOutletTask = new GetAForLightingOutletTask();
        getAForLightingOutletTask.execute();
    }




    public void getAllATsAndStartNextActivity(final Context context, final OnItemsLoadedListener listener) {
        class GetAllItemsTask extends AsyncTask<Void, Void, List<String>> {
            @Override
            protected List<String> doInBackground(Void... voids) {
                // Retrieve all items from the ProjectTable
                List<ProjectTable> projectTables = DatabaseClient.getInstance(context)
                        .getProjectDatabase()
                        .projectDAO()
                        .selectAT();
                List<String> ATList = new ArrayList<>();
                for (ProjectTable projectTable : projectTables) {
                    // Add each item to the itemList
                    ATList.add(projectTable.getAT());
                }
                return ATList;
            }

            @Override
            protected void onPostExecute(List<String> itemList) {
                super.onPostExecute(itemList);
                if (itemList != null && !itemList.isEmpty()) {
                    // Notify the listener with the list of items
                    listener.onItemsLoaded(itemList);
                } else {
                 //Toast.makeText(context, "No items found", Toast.LENGTH_SHORT).show();
                }
            }
        }

        GetAllItemsTask getAllItemsTask = new GetAllItemsTask();
        getAllItemsTask.execute();
    }



    public void getAllAsAndStartNextActivity(final Context context, final OnItemsLoadedListener listener) {
        class GetAllAsTask extends AsyncTask<Void, Void, List<String>> {
            @Override
            protected List<String> doInBackground(Void... voids) {
                // Retrieve all items from the ProjectTable
                List<ProjectTable> projectTables = DatabaseClient.getInstance(context)
                        .getProjectDatabase()
                        .projectDAO()
                        .selectA();
                List<String> AList = new ArrayList<>();
                for (ProjectTable projectTable : projectTables) {
                    // Add each item to the itemList
                    AList.add(projectTable.getA());
                }
                return AList;
            }

            @Override
            protected void onPostExecute(List<String> aList) {
                super.onPostExecute(aList);
                if (aList != null && !aList.isEmpty()) {
                    listener.onItemsLoaded(aList);
                } else {
                 //Toast.makeText(context, "No A values found", Toast.LENGTH_SHORT).show();
                }
            }
        }

        GetAllAsTask getAllAsTask = new GetAllAsTask();
        getAllAsTask.execute();
    }


    public void getAllVAsAndStartNextActivity(final Context context, final OnItemsLoadedListener listener) {
        class GetAllItemsTask extends AsyncTask<Void, Void, List<String>> {
            @Override
            protected List<String> doInBackground(Void... voids) {
                // Retrieve all items from the ProjectTable
                List<ProjectTable> projectTables = DatabaseClient.getInstance(context)
                        .getProjectDatabase()
                        .projectDAO()
                        .selectVA();
                List<String> VAList = new ArrayList<>();
                for (ProjectTable projectTable : projectTables) {
                    // Add each item to the itemList
                    VAList.add(projectTable.getVA());
                }
                return VAList;
            }

            @Override
            protected void onPostExecute(List<String> itemList) {
                super.onPostExecute(itemList);
                if (itemList != null && !itemList.isEmpty()) {
                    // Notify the listener with the list of items
                    listener.onItemsLoaded(itemList);
                } else {
                 //Toast.makeText(context, "No items found", Toast.LENGTH_SHORT).show();
                }
            }
        }

        GetAllItemsTask getAllItemsTask = new GetAllItemsTask();
        getAllItemsTask.execute();
    }
//update project
    public void updateData(ProjectTable projectTable, String ProjectName, String Quantity, String Item, String OPlus, String V, String VA, String A, String P, String AT, String AF, String SNUM, String SMM, String STYPE, String GNUM, String GMM, String GTYPE, String MMPlus, String CTYPE)
    {
        class UpdateProjectData extends AsyncTask<Void,Void,ProjectTable>
        {

            @Override
            protected ProjectTable doInBackground(Void... voids) {
                projectTable.setProjectName(ProjectName);
                projectTable.setQuantity(Quantity);
                projectTable.setItem(Item);
                projectTable.setOPlus(OPlus);
                projectTable.setV(V);
                projectTable.setVA(VA);
                projectTable.setA(A);
                projectTable.setP(P);
                projectTable.setAT(AT);
                projectTable.setAF(AF);
                projectTable.setSNUM(SNUM);
                projectTable.setSMM(SMM);
                projectTable.setSTYPE(STYPE);
                projectTable.setGNUM(GNUM);
                projectTable.setGMM(GMM);
                projectTable.setGTYPE(GTYPE);
                projectTable.setMMPlus(MMPlus);
                projectTable.setCTYPE(CTYPE);

                DatabaseClient.getInstance(context)
                        .getProjectDatabase()
                        .projectDAO()
                        .updateData(projectTable);
                return projectTable;
            }

            @Override
            protected void onPostExecute(ProjectTable projectTable) {
                super.onPostExecute(projectTable);
                if (projectTable !=null)
                {
                 //Toast.makeText(context, "UPDATED", Toast.LENGTH_SHORT).show();
                }
            }
        }
        UpdateProjectData updateProjectData = new UpdateProjectData();
        updateProjectData.execute();
    }



    // Add this method in your DatabaseHelper class
    public void clearTable() {
        class ClearTableTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                // Access the project database and delete all records from the table
                DatabaseClient.getInstance(context)
                        .getProjectDatabase()
                        .projectDAO()
                        .deleteAllProjects();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }

        // Execute the AsyncTask to clear the table
        ClearTableTask clearTableTask = new ClearTableTask();
        clearTableTask.execute();
    }
    // Assuming this is in the activity where you want to start the next activity









}

package com.example.projectone.Helper;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.room.DatabaseView;

import com.example.projectone.Databases.DatabaseClient;
import com.example.projectone.Databases.ProjectDAO;
import com.example.projectone.Databases.ProjectTable;
import com.example.projectone.Loadschedule;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    Context context;

    public DatabaseHelper (Context context)
    {
        this.context = context;
    }
    public  static  DatabaseHelper getInstance(Context context)
    {
        return new DatabaseHelper(context);
    }

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
                    Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
                }
            }
        }

        NewProject newProject = new NewProject();
        newProject.execute();

    }

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
                    Toast.makeText(context, "UPDATED", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "Fresh Project", Toast.LENGTH_SHORT).show();
            }
        }

        // Execute the AsyncTask to clear the table
        ClearTableTask clearTableTask = new ClearTableTask();
        clearTableTask.execute();
    }
    public void getAllItemsList() {
        class GetAllItemsTask extends AsyncTask<Void, Void, List<String>> {
            @Override
            protected List<String> doInBackground(Void... voids) {
                // Retrieve the list of items from the database
                return DatabaseClient.getInstance(context)
                        .getProjectDatabase()
                        .projectDAO()
                        .getAllItems();
            }

            @Override
            protected void onPostExecute(List<String> itemsList) {
                super.onPostExecute(itemsList);
                // Pass the list of items to another activity using an Intent
                if (itemsList != null && !itemsList.isEmpty()) {
                    Intent intent = new Intent(context, Loadschedule.class);
                    intent.putStringArrayListExtra("itemsList", new ArrayList<>(itemsList));
                    context.startActivity(intent);
                }
            }
        }

        // Execute the AsyncTask to fetch the list of items
        GetAllItemsTask getAllItemsTask = new GetAllItemsTask();
        getAllItemsTask.execute();
    }





}

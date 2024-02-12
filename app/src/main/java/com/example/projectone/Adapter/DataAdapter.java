package com.example.projectone.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectone.Databases.ProjectTable;
import com.example.projectone.Helper.DatabaseHelper;
import com.example.projectone.R;
import com.example.projectone.UpdateDataActivity;
import com.google.protobuf.StringValue;

import org.w3c.dom.Text;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    Context context;
    View view;
    List<ProjectTable> projectTableList;
    DatabaseHelper helper;

    public DataAdapter(Context context) {
        this.context = context;
    }

    public DataAdapter(Context context, List<ProjectTable> projectTableList) {
        this.context = context;
        this.projectTableList = projectTableList;

        helper = DatabaseHelper.getInstance(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.rv_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (projectTableList != null && projectTableList.size() > 0)
        {
            ProjectTable projectTable = projectTableList.get(position);
            holder.NumberId.setText(String.valueOf(projectTable.getId()));
            holder.Quantity.setText(String.valueOf(projectTable.getQuantity()));
            holder.Item.setText(String.valueOf(projectTable.getItem()));
            holder.OPlus.setText(String.valueOf(projectTable.getOPlus()));
            holder.V.setText(String.valueOf(projectTable.getV()));
            holder.VA.setText(String.valueOf(projectTable.getVA()));
            holder.A.setText(String.valueOf(projectTable.getA()));
            holder.P.setText(String.valueOf(projectTable.getP()));
            holder.AT.setText(String.valueOf(projectTable.getAT()));
            holder.AF.setText(String.valueOf(projectTable.getAF()));
            holder.SNUM.setText(String.valueOf(projectTable.getSNUM()));
            holder.SMM.setText(String.valueOf(projectTable.getSMM()));
            holder.STYPE.setText(String.valueOf(projectTable.getSTYPE()));
            holder.GNUM.setText(String.valueOf(projectTable.getGNUM()));
            holder.GMM.setText(String.valueOf(projectTable.getGMM()));
            holder.GTYPE.setText(String.valueOf(projectTable.getGTYPE()));
            holder.MMPlus.setText(String.valueOf(projectTable.getMMPlus()));
            holder.CTYPE.setText(String.valueOf(projectTable.getCTYPE()));

            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,UpdateDataActivity.class);
                    intent.putExtra("ProjectTable",projectTable);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return projectTableList.size()
                ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView NumberId, Quantity,Item, OPlus, V, VA, A, P, AT, AF, SNUM, SMM, STYPE, GNUM, GMM, GTYPE, MMPlus, CTYPE;
        LinearLayout edit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NumberId = itemView.findViewById(R.id.NumberId);
            Quantity = itemView.findViewById(R.id.Quantity);
            Item = itemView.findViewById(R.id.Item);
            OPlus = itemView.findViewById(R.id.OPlus);
            V = itemView.findViewById(R.id.V);
            VA = itemView.findViewById(R.id.VA);
            A = itemView.findViewById(R.id.A);
            P = itemView.findViewById(R.id.P);
            AT = itemView.findViewById(R.id.AT);
            AF = itemView.findViewById(R.id.AF);
            SNUM = itemView.findViewById(R.id.SNUM);
            SMM = itemView.findViewById(R.id.SMM);
            STYPE = itemView.findViewById(R.id.STYPE);
            GNUM = itemView.findViewById(R.id.GNUM);
            GMM = itemView.findViewById(R.id.GMM);
            GTYPE = itemView.findViewById(R.id.GTYPE);
            MMPlus = itemView.findViewById(R.id.MMPlus);
            CTYPE = itemView.findViewById(R.id.CTYPE);
            edit = itemView.findViewById(R.id.edit);

        }
    }
}

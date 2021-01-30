package com.dahumbuilders.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dahumbuilders.R;
import com.dahumbuilders.Utils;
import com.dahumbuilders.model.ProjectList;

import java.util.List;

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ViewHolder> {
    private final List<ProjectList> projectList;

    public ProjectListAdapter(List<ProjectList> projectList) {
        this.projectList = projectList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_project_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProjectList projectList = this.projectList.get(position);
        holder.block.setText("Block: " + projectList.block);
        holder.lot.setText("ProjectList: " + projectList.lot);
        holder.sqm.setText("SQM: " + projectList.sqm);
        holder.tcp.setText("TCP: " + Utils.format(projectList.tcp));
        holder.assignStat.setText("" + projectList.assignStat);

    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView block;
        TextView lot;
        TextView sqm;
        TextView tcp;
        TextView assignStat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            block = itemView.findViewById(R.id.txtBlock);
            lot = itemView.findViewById(R.id.txtLot);
            sqm = itemView.findViewById(R.id.txtSqm);
            tcp = itemView.findViewById(R.id.txtTcp);
            assignStat = itemView.findViewById(R.id.txtAsignStat);
        }
    }
}

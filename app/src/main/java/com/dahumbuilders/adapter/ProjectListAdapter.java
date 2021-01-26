package com.dahumbuilders.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dahumbuilders.R;
import com.dahumbuilders.model.Project;

import java.util.List;

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ViewHolder> {
    private List<Project> projectList;

    public ProjectListAdapter(List<Project> projectList) {
        this.projectList = projectList;
    }

    public void setProject(List<Project> projectList) {
        this.projectList = projectList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_project, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Project project = projectList.get(position);
        holder.projectName.setText(project.projName);
        holder.address.setText(project.address);
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView projectName;
        TextView address;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            projectName = itemView.findViewById(R.id.txtProjName);
            address = itemView.findViewById(R.id.txtAddress);
        }
    }
}

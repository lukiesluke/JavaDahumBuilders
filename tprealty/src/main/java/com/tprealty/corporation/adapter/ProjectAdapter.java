package com.tprealty.corporation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tprealty.corporation.R;
import com.tprealty.corporation.Utils;
import com.tprealty.corporation.model.Project;

import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {
    private List<Project> projectList;
    private OnProjectNameClickListener onProjectNameClickListener;

    public ProjectAdapter(List<Project> projectList, OnProjectNameClickListener onProjectNameClickListener) {
        this.projectList = projectList;
        this.onProjectNameClickListener = onProjectNameClickListener;
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

    public interface OnProjectNameClickListener {
        void itemClickedProjectName(View view, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView projectName;
        TextView address;
        ImageView location;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            projectName = itemView.findViewById(R.id.txtProjName);
            address = itemView.findViewById(R.id.txtAddress);
            location = itemView.findViewById(R.id.location);

            location.setOnClickListener(this);
            itemView.setOnClickListener(this);

            projectName.setTypeface(Utils.fontRegular(itemView.getContext()));
            address.setTypeface(Utils.fontLight(itemView.getContext()));
        }

        @Override
        public void onClick(View v) {
            if (onProjectNameClickListener != null) {
                Project project = projectList.get(getAdapterPosition());
                v.setTag(project);
                onProjectNameClickListener.itemClickedProjectName(v, getAdapterPosition());
            }
        }
    }
}

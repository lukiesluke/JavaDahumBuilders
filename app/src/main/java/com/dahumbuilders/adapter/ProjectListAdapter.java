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
import com.dahumbuilders.model.Lot;

import java.util.ArrayList;
import java.util.List;

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ViewHolder> {
    private List<Lot> projectList = new ArrayList<>();

    public ProjectListAdapter(List<Lot> projectList) {
        this.projectList = projectList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_project_list, parent, false);
        return new ProjectListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lot lot = projectList.get(position);
        holder.block.setText("Block: " + lot.block);
        holder.lot.setText("Lot: " + lot.lot);
        holder.sqm.setText("SQM: " + lot.sqm);
        holder.tcp.setText("TCP: " + Utils.format(lot.tcp));
        holder.asignStat.setText("" + lot.assignStat);

    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView block;
        TextView lot;
        TextView sqm;
        TextView tcp;
        TextView asignStat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            block = itemView.findViewById(R.id.txtBlock);
            lot = itemView.findViewById(R.id.txtLot);
            sqm = itemView.findViewById(R.id.txtSqm);
            tcp = itemView.findViewById(R.id.txtTcp);
            asignStat = itemView.findViewById(R.id.txtAsignStat);
        }
    }
}

package com.dahumbuilders.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dahumbuilders.R;
import com.dahumbuilders.Utils;
import com.dahumbuilders.model.ProjectList;

import java.util.ArrayList;
import java.util.List;

import static com.dahumbuilders.R.color.colorGreen;
import static com.dahumbuilders.R.color.colorRed;

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ViewHolder> implements Filterable {
    private final List<ProjectList> projectList;
    private List<ProjectList> projectListFilterable;

    public ProjectListAdapter(List<ProjectList> projectList) {
        this.projectList = projectList;
        this.projectListFilterable = projectList;
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
        ProjectList projectList = this.projectListFilterable.get(position);
        holder.block.setText("Block: " + projectList.block);
        holder.lot.setText("Lot: " + projectList.lot);
        holder.sqm.setText("SQM: " + projectList.sqm);
        holder.tcp.setText("TCP: " + Utils.format(projectList.tcp));
        holder.name.setText("Client Name: " + projectList.name);

        if (projectList.assignStat > 0) {
            holder.assignStat.setTextColor(holder.getContext().getResources().getColor(colorRed));
            holder.assignStat.setText("Not Available");
            holder.name.setVisibility(View.VISIBLE);
        } else {
            holder.assignStat.setText("Available");
            holder.assignStat.setTextColor(holder.getContext().getResources().getColor(colorGreen));
            holder.name.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return projectListFilterable.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                int searchBlock;
                if (charString.isEmpty()) {
                    searchBlock = 0;
                } else {
                    try {
                        searchBlock = Integer.parseInt(constraint.toString());
                    } catch (Exception e) {
                        searchBlock = 0;
                    }
                }

                if (searchBlock <= 0) {
                    projectListFilterable = projectList;
                } else {
                    List<ProjectList> filterList = new ArrayList<>();
                    for (ProjectList row : projectList) {
                        if (row.getBlock() == searchBlock) {
                            filterList.add(row);
                        }
                    }
                    projectListFilterable = filterList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = projectListFilterable;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
                projectListFilterable = (ArrayList<ProjectList>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView block;
        TextView lot;
        TextView sqm;
        TextView tcp;
        TextView assignStat;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            block = itemView.findViewById(R.id.txtBlock);
            lot = itemView.findViewById(R.id.txtLot);
            sqm = itemView.findViewById(R.id.txtSqm);
            tcp = itemView.findViewById(R.id.txtTcp);
            assignStat = itemView.findViewById(R.id.txtAssignStat);
            name = itemView.findViewById(R.id.txtName);

            block.setTypeface(Utils.fontRegular(itemView.getContext()));
            lot.setTypeface(Utils.fontRegular(itemView.getContext()));
            sqm.setTypeface(Utils.fontRegular(itemView.getContext()));
            tcp.setTypeface(Utils.fontRegular(itemView.getContext()));
            assignStat.setTypeface(Utils.fontRegular(itemView.getContext()));
            name.setTypeface(Utils.fontRegular(itemView.getContext()));
        }

        public Context getContext() {
            return itemView.getContext();
        }
    }
}

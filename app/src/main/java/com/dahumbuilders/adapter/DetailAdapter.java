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
import com.dahumbuilders.model.Detail;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    private List<Detail> detailList;

    public DetailAdapter(List<Detail> detailList) {
        this.detailList = detailList;
    }

    public void setDetail(List<Detail> detailList) {
        this.detailList = detailList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Detail detail = detailList.get(position);

        holder.projName.setText(detail.projName);
        holder.totalCash.setText(Utils.format(detail.cash));
        holder.totalExpenses.setText(Utils.format(detail.expenses));
    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView projName, totalCash, totalExpenses;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            projName = itemView.findViewById(R.id.txtProjName);
            totalCash = itemView.findViewById(R.id.txtTotalCash);
            totalExpenses = itemView.findViewById(R.id.txtTotalExpenses);
        }
    }
}

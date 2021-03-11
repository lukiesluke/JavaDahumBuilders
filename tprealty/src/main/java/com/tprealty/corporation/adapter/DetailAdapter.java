package com.tprealty.corporation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tprealty.corporation.R;
import com.tprealty.corporation.Utils;

import java.util.List;

import com.tprealty.corporation.model.Detail;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    private final List<Detail> detailList;

    public DetailAdapter(List<Detail> detailList) {
        this.detailList = detailList;
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
        holder.cash.setText(Utils.format(detail.cash));
        holder.expenses.setText(Utils.format(detail.expenses));
        holder.bankTransfer.setText(Utils.format(detail.bankTransfer));
        holder.check.setText(Utils.format(detail.check));
    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView projName, cash, expenses, bankTransfer, check;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            projName = itemView.findViewById(R.id.txtProjName);
            cash = itemView.findViewById(R.id.txtCash);
            expenses = itemView.findViewById(R.id.txtExpenses);
            bankTransfer = itemView.findViewById(R.id.txtBankTransfer);
            check = itemView.findViewById(R.id.txtCheck);

            projName.setTypeface(Utils.fontRegular(itemView.getContext()));
            cash.setTypeface(Utils.fontLight(itemView.getContext()));
            expenses.setTypeface(Utils.fontLight(itemView.getContext()));
            bankTransfer.setTypeface(Utils.fontLight(itemView.getContext()));
            check.setTypeface(Utils.fontLight(itemView.getContext()));
        }
    }
}

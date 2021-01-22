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
import com.dahumbuilders.model.Summary;

import java.util.List;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.ViewHolder> {
    private List<Summary> summaryList;
    private OnSummaryClickListener onSummaryClickListener;

    public SummaryAdapter(List<Summary> summaryList, OnSummaryClickListener onSummaryClickListener) {
        this.summaryList = summaryList;
        this.onSummaryClickListener = onSummaryClickListener;
    }

    public void setSummary(List<Summary> summaryList) {
        this.summaryList = summaryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_summary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Summary current = summaryList.get(position);

        holder.date.setText(Utils.stringToDate(current.datePaid));
        holder.cash.setText(Utils.format(current.totalCash));
        holder.expenses.setText(Utils.format(current.expenses));
        holder.check.setText(Utils.format(current.totalCheck));
        holder.bankTransfer.setText(Utils.format(current.totalBankTransfer));
    }

    @Override
    public int getItemCount() {
        return summaryList.size();
    }

    public interface OnSummaryClickListener {
        void itemClickedSummary(View view, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView date, cash, expenses, check, bankTransfer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            date = itemView.findViewById(R.id.txtDatePaid);
            cash = itemView.findViewById(R.id.txtCash);
            expenses = itemView.findViewById(R.id.txtExpenses);
            check = itemView.findViewById(R.id.txtCheck);
            bankTransfer = itemView.findViewById(R.id.txtBankTransfer);
        }

        @Override
        public void onClick(View v) {
            if (onSummaryClickListener != null) {
                Summary summary = summaryList.get(getAdapterPosition());
                v.setTag(summary);
                onSummaryClickListener.itemClickedSummary(v, getAdapterPosition());
            }
        }
    }
}

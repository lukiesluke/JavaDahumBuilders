package com.tprealty.corporation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tprealty.corporation.R;
import com.tprealty.corporation.Utils;

import java.util.ArrayList;
import java.util.List;

import com.tprealty.corporation.model.Summary;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.ViewHolder> implements Filterable {
    private List<Summary> summaryList;
    private List<Summary> summaryListFilterable;
    private final OnSummaryClickListener onSummaryClickListener;

    public SummaryAdapter(List<Summary> summaryList, OnSummaryClickListener onSummaryClickListener) {
        this.summaryList = summaryList;
        this.summaryListFilterable = summaryList;
        this.onSummaryClickListener = onSummaryClickListener;
    }

    public void setSummary(List<Summary> summaryList) {
        this.summaryList = summaryList;
        this.summaryListFilterable = summaryList;
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
        Summary current = summaryListFilterable.get(position);
        holder.date.setText(Utils.stringToDate(current.datePaid));
        holder.cash.setText(Utils.format(current.totalCash));
        holder.expenses.setText(Utils.format(current.expenses));
        holder.check.setText(Utils.format(current.totalCheck));
        holder.bankTransfer.setText(Utils.format(current.totalBankTransfer));
    }

    @Override
    public int getItemCount() {
        return summaryListFilterable.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    summaryListFilterable = summaryList;
                } else {
                    List<Summary> filterList = new ArrayList<>();
                    for (Summary row : summaryList) {
                        if (row.getDatePaid().toLowerCase().contains(charString.toLowerCase())) {
                            filterList.add(row);
                        }
                    }
                    summaryListFilterable = filterList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = summaryListFilterable;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
                summaryListFilterable = (ArrayList<Summary>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface OnSummaryClickListener {
        void itemClickedSummary(View view, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView date;
        private final TextView cash;
        private final TextView expenses;
        private final TextView check;
        private final TextView bankTransfer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            date = itemView.findViewById(R.id.txtDatePaid);
            cash = itemView.findViewById(R.id.txtCash);
            expenses = itemView.findViewById(R.id.txtExpenses);
            check = itemView.findViewById(R.id.txtCheck);
            bankTransfer = itemView.findViewById(R.id.txtBankTransfer);

            date.setTypeface(Utils.fontRegular(itemView.getContext()));
            cash.setTypeface(Utils.fontLight(itemView.getContext()));
            expenses.setTypeface(Utils.fontLight(itemView.getContext()));
            check.setTypeface(Utils.fontLight(itemView.getContext()));
            bankTransfer.setTypeface(Utils.fontLight(itemView.getContext()));
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

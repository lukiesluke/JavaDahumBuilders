package com.dahumbuilders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dahumbuilders.model.Summary;

import java.util.List;

public class SummaryAdaptor extends RecyclerView.Adapter<SummaryAdaptor.ViewHolder> {
    private List<Summary> summaryList;
    private OnSummaryClickListener onSummaryClickListener;

    public SummaryAdaptor(OnSummaryClickListener onSummaryClickListener) {
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

        holder.date.setText(current.datePaid);
        holder.totalCash.setText(current.totalCash + "");
        holder.expenses.setText(current.expenses + "");
    }

    @Override
    public int getItemCount() {
        return summaryList.size();
    }

    public interface OnSummaryClickListener {
        void itemClickedSummary(View view, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView date, totalCash, expenses;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            date = itemView.findViewById(R.id.txtDate);
            totalCash = itemView.findViewById(R.id.txtTotalCash);
            expenses = itemView.findViewById(R.id.txtExpenses);
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

package com.dahumbuilders.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dahumbuilders.R;
import com.dahumbuilders.Utils;
import com.dahumbuilders.adapter.DetailAdapter;
import com.dahumbuilders.model.Detail;
import com.dahumbuilders.model.Summary;

import java.util.List;

public class DetailFragment extends BaseFragment {

    private static final String ARG_DETAIL = "param1";
    private Summary summary = new Summary();

    public DetailFragment() {
    }

    public static DetailFragment newInstance(String detailReport) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DETAIL, detailReport);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mDetailReport = getArguments().getString(ARG_DETAIL);
            summary = gson.fromJson(mDetailReport, Summary.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        RecyclerView recyclerView = view.findViewById(R.id.recycler);

        TextView datePaid = view.findViewById(R.id.txtDatePaid);
        TextView totalCash = view.findViewById(R.id.txtCash);
        TextView totalExpenses = view.findViewById(R.id.txtExpenses);
        TextView bankTransfer = view.findViewById(R.id.txtBankTransfer);
        TextView check = view.findViewById(R.id.txtCheck);

        datePaid.setText(Utils.stringToDate(summary.datePaid));
        totalCash.setText(Utils.format(summary.totalCash));
        totalExpenses.setText(Utils.format(summary.expenses));
        bankTransfer.setText(Utils.format(summary.totalBankTransfer));
        check.setText(Utils.format(summary.totalCheck));

        List<Detail> detailList = summary.details;

        DetailAdapter adapter = new DetailAdapter(detailList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setEnabled(false);
        return view;
    }
}
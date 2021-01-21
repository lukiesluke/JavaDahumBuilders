package com.dahumbuilders.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dahumbuilders.R;
import com.dahumbuilders.Utils;
import com.dahumbuilders.adapter.DetailAdapter;
import com.dahumbuilders.model.Detail;
import com.dahumbuilders.model.Summary;
import com.google.gson.Gson;

import java.util.List;

public class DetailFragment extends Fragment {

    private static final String ARG_DETAIL = "param1";
    private Summary summary = new Summary();
    private Gson gson = new Gson();

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

        TextView datePaid = view.findViewById(R.id.txtDate);
        TextView totalCash = view.findViewById(R.id.txtTotalCash);
        TextView totalExpenses = view.findViewById(R.id.txtExpenses);

        datePaid.setText(Utils.stringToDate(summary.datePaid));
        totalCash.setText(Utils.format(summary.totalCash));
        totalExpenses.setText(Utils.format(summary.expenses));
        List<Detail> detailList = summary.details;

        DetailAdapter adapter = new DetailAdapter(detailList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setEnabled(false);
        return view;
    }
}
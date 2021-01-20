package com.dahumbuilders.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.dahumbuilders.R;
import com.dahumbuilders.Utils;
import com.dahumbuilders.model.Summary;
import com.google.gson.Gson;

public class DetailFragment extends Fragment {

    private static final String ARG_DETAIL = "param1";
    private Gson gson = new Gson();
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

        TextView datePaid = view.findViewById(R.id.txtDate);
        TextView totalCash = view.findViewById(R.id.txtTotalCash);
        TextView totalExpenses = view.findViewById(R.id.txtExpenses);

        datePaid.setText(summary.datePaid);
        totalCash.setText(Utils.format(summary.totalCash));
        totalExpenses.setText(Utils.format(summary.expenses));
        return view;
    }
}
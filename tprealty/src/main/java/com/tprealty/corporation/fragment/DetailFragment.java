package com.tprealty.corporation.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tprealty.corporation.R;
import com.tprealty.corporation.Utils;
import com.tprealty.corporation.adapter.DetailAdapter;
import com.tprealty.corporation.presenter.DetailPresenter;
import com.tprealty.corporation.presenter.IDetail;

import java.util.List;

import com.tprealty.corporation.model.Detail;
import com.tprealty.corporation.model.Summary;

public class DetailFragment extends BaseFragment implements IDetail {

    private static final String ARG_DETAIL_REPORT = "param1";
    private Summary summary = new Summary();
    private DetailPresenter presenter;
    private RecyclerView recyclerView;
    private TextView header;
    private TextView datePaid;
    private TextView totalCash;
    private TextView totalExpenses;
    private TextView bankTransfer;
    private TextView check;
    private TextView labelDetail;

    protected Typeface tfBold;
    protected Typeface tfRegular;

    public DetailFragment() {
    }

    public static DetailFragment newInstance(String detailReport) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DETAIL_REPORT, detailReport);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mDetailReport = getArguments().getString(ARG_DETAIL_REPORT);
            summary = gson.fromJson(mDetailReport, Summary.class);
        } else {
            summary = new Summary();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setEnabled(false);
        recyclerView = view.findViewById(R.id.recycler);

        tfBold = Typeface.createFromAsset(context.getAssets(), "OpenSans-Bold.ttf");
        tfRegular = Typeface.createFromAsset(context.getAssets(), "OpenSans-Regular.ttf");

        header = view.findViewById(R.id.txtHeader);
        datePaid = view.findViewById(R.id.txtDatePaid);
        totalCash = view.findViewById(R.id.txtCash);
        totalExpenses = view.findViewById(R.id.txtExpenses);
        bankTransfer = view.findViewById(R.id.txtBankTransfer);
        check = view.findViewById(R.id.txtCheck);
        labelDetail = view.findViewById(R.id.txtLabelDetail);

        presenter = new DetailPresenter(getContext(), this);
        presenter.ready();
        return view;
    }

    @Override
    public void init() {
        datePaid.setText(Utils.stringToDate(summary.datePaid));
        datePaid.setTypeface(tfRegular);
        totalCash.setText(Utils.format(summary.totalCash));
        totalCash.setTypeface(tfBold);
        totalExpenses.setText(Utils.format(summary.expenses));
        totalExpenses.setTypeface(tfBold);
        bankTransfer.setText(Utils.format(summary.totalBankTransfer));
        bankTransfer.setTypeface(tfBold);
        check.setText(Utils.format(summary.totalCheck));
        check.setTypeface(tfBold);

        header.setTypeface(tfBold);
        labelDetail.setTypeface(tfRegular);

        List<Detail> detailList = summary.details;
        DetailAdapter adapter = new DetailAdapter(detailList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }
}

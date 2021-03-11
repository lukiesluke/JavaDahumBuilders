package com.tprealty.corporation.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tprealty.corporation.R;
import com.tprealty.corporation.activity.DetailActivity;
import com.tprealty.corporation.adapter.SummaryAdapter;
import com.tprealty.corporation.presenter.ISummary;
import com.tprealty.corporation.presenter.SummaryPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.tprealty.corporation.model.Summary;

public class SummaryFragment extends BaseFragment implements SummaryAdapter.OnSummaryClickListener, ISummary {

    private SummaryAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SummaryPresenter presenter;
    private RecyclerView recyclerView;
    private final List<Summary> summaryList = new ArrayList<>();

    public SummaryFragment() {
    }

    public static SummaryFragment newInstance() {
        return new SummaryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);

        presenter = new SummaryPresenter(getContext(), this);
        presenter.ready();
        return view;
    }

    @Override
    public void init() {
        swipeRefreshLayout.setEnabled(false);

        /*adapter = new SummaryAdapter(presenter.getSummaryListCachedFile(), this);*/
        adapter = new SummaryAdapter(summaryList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        presenter.requestFromFirebase();
    }

    @Override
    public void requestFirebaseOnDataChange(List<Summary> summaryList) {
        adapter.setSummary(summaryList);
    }

    @Override
    public void itemClickedSummary(View view, int position) {
        Summary summary = (Summary) view.getTag();
        String summaryString = gson.toJson(summary);

        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.KEY_SUMMARY, summaryString);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).overridePendingTransition(R.xml.enter, R.xml.exit);
    }
}
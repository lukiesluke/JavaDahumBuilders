package com.dahumbuilders.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dahumbuilders.presenter.ISummary;
import com.dahumbuilders.R;
import com.dahumbuilders.presenter.SummaryPresenter;
import com.dahumbuilders.activity.DetailActivity;
import com.dahumbuilders.adapter.SummaryAdapter;
import com.dahumbuilders.model.Summary;

import java.util.List;
import java.util.Objects;

public class SummaryFragment extends BaseFragment implements SummaryAdapter.OnSummaryClickListener, ISummary {

    private SummaryAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SummaryPresenter presenter;
    private RecyclerView recyclerView;

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
        adapter = new SummaryAdapter(presenter.getSummaryListCachedFile(), this);
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
        intent.putExtra(DetailActivity.SUMMARY_KEY, summaryString);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).overridePendingTransition(R.xml.enter, R.xml.exit);
    }
}
package com.dahumbuilders.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dahumbuilders.R;
import com.dahumbuilders.activity.DetailActivity;
import com.dahumbuilders.adapter.SummaryAdapter;
import com.dahumbuilders.model.Summary;
import com.dahumbuilders.presenter.ISummary;
import com.dahumbuilders.presenter.SummaryPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SummaryFragment extends BaseFragment implements SummaryAdapter.OnSummaryClickListener, ISummary {

    private final List<Summary> summaryList = new ArrayList<>();
    private SummaryAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SummaryPresenter presenter;
    private RecyclerView recyclerView;
    private boolean isLogin;
    private TextView txtPassword, errorMassage;
    private View layoutViewList, layoutViewLogin;

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
        txtPassword = view.findViewById(R.id.txtPassword);
        errorMassage = view.findViewById(R.id.errorMassage);
        layoutViewList = view.findViewById(R.id.layoutViewList);

        layoutViewLogin = view.findViewById(R.id.layoutViewLogin);

        Button buttonLogin = view.findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("88admin".contentEquals(txtPassword.getText())) {
                    layoutViewList.setVisibility(View.VISIBLE);
                    isLogin = true;
                    layoutViewLogin.setVisibility(View.GONE);
                } else {
                    errorMassage.setVisibility(View.VISIBLE);
                    layoutViewList.setVisibility(View.GONE);
                    if (txtPassword.getText().toString().isEmpty()) {
                        errorMassage.setText(R.string.enter_password);
                    } else {
                        errorMassage.setText(R.string.invalid_password);
                    }
                    isLogin = false;
                }
                hideKeyboard();
            }
        });

        presenter = new SummaryPresenter(getContext(), this);
        presenter.ready();
        return view;
    }

    @Override
    public void init() {
        swipeRefreshLayout.setEnabled(false);
        setLayoutView();

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

    private void setLayoutView() {
        if (isLogin) {
            layoutViewList.setVisibility(View.VISIBLE);
            layoutViewLogin.setVisibility(View.GONE);
        } else {
            layoutViewList.setVisibility(View.GONE);
            layoutViewLogin.setVisibility(View.VISIBLE);
        }
    }

    private void hideKeyboard() {
        ((InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }
}
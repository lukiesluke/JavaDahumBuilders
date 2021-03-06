package com.dahumbuilders.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dahumbuilders.R;
import com.dahumbuilders.Utils;
import com.dahumbuilders.activity.DetailActivity;
import com.dahumbuilders.adapter.SummaryAdapter;
import com.dahumbuilders.model.ResponseSummary;
import com.dahumbuilders.model.Summary;
import com.dahumbuilders.network.GetDataService;
import com.dahumbuilders.network.RetrofitClientInstance;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dahumbuilders.network.Constant.PRE_KEY_SUMMARY;

public class SummaryFragment extends BaseFragment implements SummaryAdapter.OnSummaryClickListener {

    private SummaryAdapter adapter;
    private List<Summary> summaryList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;

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
        RecyclerView recyclerView = view.findViewById(R.id.recycler);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(this::fetchFromService);

        adapter = new SummaryAdapter(summaryList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        fetchFromService();
        return view;
    }

    private void fetchFromService() {
        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ResponseSummary> call = service.getAllPhotos();
        call.enqueue(new Callback<ResponseSummary>() {
            @Override
            public void onResponse(@NonNull Call<ResponseSummary> call, @NonNull Response<ResponseSummary> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        summaryList = response.body().summary;
                        adapter.setSummary(response.body().summary);
                        Utils.putPref(context, PRE_KEY_SUMMARY, gson.toJson(summaryList));
                    }
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<ResponseSummary> call, @NonNull Throwable t) {
                String response = Utils.getPref(context, PRE_KEY_SUMMARY);
                if (response.length() > 2) {
                    Type listType = new TypeToken<ArrayList<Summary>>() {
                    }.getType();
                    List<Summary> summaryList = new Gson().fromJson(response, listType);
                    adapter.setSummary(summaryList);
                }
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_LONG).show();
            }
        });
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
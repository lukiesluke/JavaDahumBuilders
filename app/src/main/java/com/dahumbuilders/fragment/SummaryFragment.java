package com.dahumbuilders.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dahumbuilders.R;
import com.dahumbuilders.adapter.SummaryAdaptor;
import com.dahumbuilders.model.ResponseSummary;
import com.dahumbuilders.model.Summary;
import com.dahumbuilders.network.GetDataService;
import com.dahumbuilders.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummaryFragment extends Fragment implements SummaryAdaptor.OnSummaryClickListener {
    private SummaryAdaptor adapter;
    private List<Summary> summaryList = new ArrayList<>();

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

        adapter = new SummaryAdaptor(summaryList, this);
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
                    summaryList = response.body().summary;
                    adapter.setSummary(response.body().summary);
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseSummary> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void itemClickedSummary(View view, int position) {
        Summary summary = (Summary) view.getTag();
        Log.d("lwg", summary.datePaid + " " + summary.details.get(0).projName);
    }
}
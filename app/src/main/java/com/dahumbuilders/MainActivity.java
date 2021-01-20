package com.dahumbuilders;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dahumbuilders.model.ResponseSummary;
import com.dahumbuilders.model.Summary;
import com.dahumbuilders.network.GetDataService;
import com.dahumbuilders.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SummaryAdaptor adapter;
    private List<Summary> summaryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);

        adapter = new SummaryAdaptor();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setSummary(summaryList);

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ResponseSummary> call = service.getAllPhotos();
        call.enqueue(new Callback<ResponseSummary>() {
            @Override
            public void onResponse(@NonNull Call<ResponseSummary> call, @NonNull Response<ResponseSummary> response) {
                if (response.isSuccessful()) {
                    summaryList = response.body().summary;
                    adapter.setSummary(response.body().summary);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseSummary> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
package com.dahumbuilders.presenter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.dahumbuilders.Utils;
import com.dahumbuilders.model.ResponseSummary;
import com.dahumbuilders.model.Summary;
import com.dahumbuilders.network.GetDataService;
import com.dahumbuilders.network.RetrofitClientInstance;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dahumbuilders.network.Constant.FB_REF_SUMMARY_TEST;
import static com.dahumbuilders.network.Constant.PRE_KEY_SUMMARY;

public class SummaryPresenter {
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private List<Summary> summaryList = new ArrayList<>();
    private final Gson gson = new Gson();
    private final Context context;
    private final ISummary view;

    public SummaryPresenter(Context context, ISummary view) {
        this.context = context;
        this.view = view;
    }

    public void ready() {
        view.init();
    }

    public void requestFromFirebase() {
        DatabaseReference databaseReference = firebaseDatabase.getReference().child(FB_REF_SUMMARY_TEST);
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Summary> arrayList = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    arrayList.add(dataSnapshot.getValue(Summary.class));
                }

                if (arrayList.size() > 0) {
                    summaryList.clear();
                    summaryList = arrayList;
                    view.requestFirebaseOnDataChange(summaryList);
                    Utils.putPref(context, PRE_KEY_SUMMARY, gson.toJson(summaryList));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public List<Summary> getSummaryListCachedFile() {
        String cachedSummary = Utils.getPref(context, PRE_KEY_SUMMARY);
        if (cachedSummary.length() > 2) {
            Type listType = new TypeToken<ArrayList<Summary>>() {
            }.getType();
            return summaryList = new Gson().fromJson(cachedSummary, listType);
        } else {
            return new ArrayList<>();
        }
    }

    public void fetchFromService() {
        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ResponseSummary> call = service.getAllPhotos();
        call.enqueue(new Callback<ResponseSummary>() {
            @Override
            public void onResponse(@NonNull Call<ResponseSummary> call, @NonNull Response<ResponseSummary> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        summaryList = response.body().summary;
                        Utils.putPref(context, PRE_KEY_SUMMARY, gson.toJson(summaryList));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseSummary> call, @NonNull Throwable t) {
                String response = Utils.getPref(context, PRE_KEY_SUMMARY);
                if (response.length() > 2) {
                    Type listType = new TypeToken<ArrayList<Summary>>() {
                    }.getType();
                    List<Summary> summaryList = new Gson().fromJson(response, listType);
                }
            }
        });
    }
}

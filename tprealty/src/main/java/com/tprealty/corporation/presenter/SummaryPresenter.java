package com.tprealty.corporation.presenter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tprealty.corporation.Utils;
import com.tprealty.corporation.model.Logs;
import com.tprealty.corporation.model.Summary;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.tprealty.corporation.network.Constant.FB_REF_SUMMARY_LOGS;
import static com.tprealty.corporation.network.Constant.FB_REF_SUMMARY_TEST;
import static com.tprealty.corporation.network.Constant.PRE_KEY_SUMMARY;

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

    public void requestFromFirebaseDateLogs() {
        DatabaseReference databaseReference = firebaseDatabase.getReference().child(FB_REF_SUMMARY_LOGS);
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Logs logs = snapshot.getValue(Logs.class);
                if (logs != null && !logs.getDatetimeLog().isEmpty()) {
                    view.requestFirebaseOnDataChangeLog(logs.getDatetimeLog(), logs.getUserInfo());
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
}

package com.dahumbuilders.presenter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.dahumbuilders.Utils;
import com.dahumbuilders.model.Logs;
import com.dahumbuilders.model.Project;
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

import static com.dahumbuilders.network.Constant.FB_REF_PROJECT_LOGS;
import static com.dahumbuilders.network.Constant.FB_REF_PROJECT_TEST;
import static com.dahumbuilders.network.Constant.PRE_KEY_PROJECT;

public class ProjectPresenter {
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private List<Project> projectList = new ArrayList<>();
    private final Gson gson = new Gson();
    private final Context context;
    private final IPresenter view;

    public ProjectPresenter(Context context, IPresenter view) {
        this.context = context;
        this.view = view;
    }

    public void ready() {
        view.init();
    }

    public void requestFromFirebase() {
        DatabaseReference databaseReference = firebaseDatabase.getReference().child(FB_REF_PROJECT_TEST);
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Project> arrayList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    arrayList.add(dataSnapshot.getValue(Project.class));
                }

                if (arrayList.size() > 0) {
                    projectList.clear();
                    projectList = arrayList;
                    view.requestFirebaseOnDataChange(projectList);
                    Utils.putPref(context, PRE_KEY_PROJECT, gson.toJson(projectList));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void requestFromFirebaseDateLogs() {
        DatabaseReference databaseReference = firebaseDatabase.getReference().child(FB_REF_PROJECT_LOGS);
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

    public List<Project> getProjectListCachedFile() {
        String cachedProject = Utils.getPref(context, PRE_KEY_PROJECT, "{}");
        if (cachedProject.length() > 2) {
            Type listType = new TypeToken<ArrayList<Project>>() {
            }.getType();
            return projectList = new Gson().fromJson(cachedProject, listType);
        } else {
            return new ArrayList<>();
        }
    }
}

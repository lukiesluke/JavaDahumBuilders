package com.dahumbuilders.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dahumbuilders.R;
import com.dahumbuilders.Utils;
import com.dahumbuilders.adapter.ProjectListAdapter;
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

import static com.dahumbuilders.network.Constant.FB_REF_PROJECT_TEST;
import static com.dahumbuilders.network.Constant.PRE_KEY_PROJECT;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProjectListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectListFragment extends BaseFragment implements ProjectListAdapter.OnProjectNameClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ProjectListAdapter adapter;
    private List<Project> projectList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;

    public ProjectListFragment() {
    }

    public static ProjectListFragment newInstance(String param1, String param2) {
        ProjectListFragment fragment = new ProjectListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);

        swipeRefreshLayout.setEnabled(false);
        getProjectListCachedFile();
        adapter = new ProjectListAdapter(projectList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        fetchFromFirebase();
        return view;
    }

    private void fetchFromFirebase() {
        databaseReference = firebaseDatabase.getReference().child(FB_REF_PROJECT_TEST);
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
                    adapter.setProject(arrayList);
                    Utils.putPref(context, PRE_KEY_PROJECT, gson.toJson(projectList));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getProjectListCachedFile() {
        String cachedProject = Utils.getPref(context, PRE_KEY_PROJECT);
        if (cachedProject.length() > 2) {
            Type listType = new TypeToken<ArrayList<Project>>() {
            }.getType();
            projectList = new Gson().fromJson(cachedProject, listType);
        }
    }

    @Override
    public void itemClickedProjectName(View view, int position) {
        Project project = (Project) view.getTag();
        Toast.makeText(getContext().getApplicationContext(), "Project list is under maintenance: " + project.getProjName(), Toast.LENGTH_SHORT).show();
    }
}
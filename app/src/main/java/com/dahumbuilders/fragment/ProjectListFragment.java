package com.dahumbuilders.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dahumbuilders.R;
import com.dahumbuilders.activity.ProjectActivity;
import com.dahumbuilders.adapter.ProjectAdapter;
import com.dahumbuilders.model.Project;
import com.dahumbuilders.presenter.IPresenter;
import com.dahumbuilders.presenter.ProjectPresenter;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProjectListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectListFragment extends BaseFragment implements ProjectAdapter.OnProjectNameClickListener, IPresenter {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ProjectAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProjectPresenter presenter;
    private RecyclerView recyclerView;

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
        recyclerView = view.findViewById(R.id.recycler);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);

        presenter = new ProjectPresenter(getContext(), this);
        presenter.ready();
        return view;
    }

    @Override
    public void init() {
        swipeRefreshLayout.setEnabled(false);

        adapter = new ProjectAdapter(presenter.getProjectListCachedFile(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        presenter.requestFromFirebase();
    }

    @Override
    public void requestFirebaseOnDataChange(List<Project> projectList) {
        adapter.setProject(projectList);
    }

    @Override
    public void itemClickedProjectName(View view, int position) {
        Project project = (Project) view.getTag();
        String projectString = gson.toJson(project);

        Intent intent = new Intent(getActivity(), ProjectActivity.class);
        intent.putExtra(ProjectActivity.KEY_PROJECT, projectString);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).overridePendingTransition(R.xml.enter, R.xml.exit);
        Toast.makeText(getContext().getApplicationContext(), "Project list is under maintenance: " + project.getProjName(), Toast.LENGTH_SHORT).show();
    }
}
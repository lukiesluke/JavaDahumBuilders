package com.tprealty.corporation.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tprealty.corporation.R;
import com.tprealty.corporation.activity.ProjectActivity;
import com.tprealty.corporation.adapter.ProjectAdapter;
import com.tprealty.corporation.model.Project;
import com.tprealty.corporation.presenter.IPresenter;
import com.tprealty.corporation.presenter.ProjectPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ProjectListFragment extends BaseFragment implements ProjectAdapter.OnProjectNameClickListener, IPresenter {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ProjectAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProjectPresenter presenter;
    private RecyclerView recyclerView;
    private final List<Project> projectList = new ArrayList<>();

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

        /*adapter = new ProjectAdapter(presenter.getProjectListCachedFile(), this);*/
        adapter = new ProjectAdapter(projectList, this);
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

        if (view.getId() == R.id.location) {
            Intent intent = new Intent(getActivity(), ProjectActivity.class);
            intent.putExtra(ProjectActivity.KEY_PROJECT, projectString);
            startActivity(intent);
            Objects.requireNonNull(getActivity()).overridePendingTransition(R.xml.enter, R.xml.exit);
        } else {
            Intent intent = new Intent(getActivity(), ProjectActivity.class);
            intent.putExtra(ProjectActivity.KEY_PROJECT, projectString);
            startActivity(intent);
            Objects.requireNonNull(getActivity()).overridePendingTransition(R.xml.enter, R.xml.exit);
        }
    }
}

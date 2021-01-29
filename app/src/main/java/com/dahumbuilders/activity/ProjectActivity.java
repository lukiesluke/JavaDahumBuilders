package com.dahumbuilders.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dahumbuilders.R;
import com.dahumbuilders.Utils;
import com.dahumbuilders.adapter.ProjectListAdapter;
import com.dahumbuilders.model.Project;
import com.google.gson.Gson;

public class ProjectActivity extends AppCompatActivity {
    public static final String KEY_PROJECT = "key_project";
    private ProjectListAdapter adapter;

    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        TextView appBarTitle = findViewById(R.id.appBarTitle);
        appBarTitle.setTypeface(Utils.fontBold(getAssets()));
        TextView projectName = findViewById(R.id.txtProjectName);
        TextView projectAddress = findViewById(R.id.txtProjectAddress);
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setEnabled(false);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Project project = gson.fromJson(bundle.getString(KEY_PROJECT), Project.class);
            projectName.setText(project.projName);
            projectName.setTypeface(Utils.fontRegular(getAssets()));
            projectAddress.setText(project.address);
            projectName.setTypeface(Utils.fontLight(getAssets()));

            Log.d("lwg", "Project Name: " + project.projName);
            adapter = new ProjectListAdapter(project.projectList);
            RecyclerView recyclerView = findViewById(R.id.recycler);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
    }
}

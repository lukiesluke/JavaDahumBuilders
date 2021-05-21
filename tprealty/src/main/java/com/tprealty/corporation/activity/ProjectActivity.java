package com.tprealty.corporation.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.tprealty.corporation.R;
import com.tprealty.corporation.Utils;
import com.tprealty.corporation.adapter.ProjectListAdapter;
import com.tprealty.corporation.model.Project;
import com.tprealty.corporation.network.Constant;

public class ProjectActivity extends BaseActivity {
    public static final String KEY_PROJECT = "key_project";
    private ProjectListAdapter adapter;
    private EditText search;
    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        TextView appBarTitle = findViewById(R.id.appBarTitle);
        appBarTitle.setTypeface(Utils.fontBold(getAssets()));

        LinearLayout linearLayout = findViewById(R.id.layout_holder);
        setupUI(linearLayout, ProjectActivity.this);

        TextView projectName = findViewById(R.id.txtProjectName);
        TextView projectAddress = findViewById(R.id.txtProjectAddress);
        search = findViewById(R.id.search);
        search.setTypeface(Utils.fontLight(getApplicationContext()));

        Button clear = findViewById(R.id.btClear);
        clear.setOnClickListener(v -> {
            search.setText(Constant.EMPTY);
        });

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

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
    }
}

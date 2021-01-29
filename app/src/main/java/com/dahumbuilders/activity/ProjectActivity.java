package com.dahumbuilders.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dahumbuilders.R;
import com.dahumbuilders.Utils;
import com.dahumbuilders.model.Project;
import com.google.gson.Gson;

public class ProjectActivity extends AppCompatActivity {
    public static final String KEY_PROJECT = "key_project";
    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        TextView appBarTitle = findViewById(R.id.appBarTitle);
        appBarTitle.setTypeface(Utils.fontBold(getAssets()));
        TextView projectName = findViewById(R.id.txtProjectName);
        TextView projectAddress = findViewById(R.id.txtProjectAddress);

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
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.your_placeholder, DetailFragment.newInstance(bundle.getString(KEY_DETAIL)));
//            fragmentTransaction.commit();
            projectName.setText(project.projName);
            projectName.setTypeface(Utils.fontRegular(getAssets()));
            projectAddress.setText(project.address);
            projectName.setTypeface(Utils.fontLight(getAssets()));
            Log.d("lwg", "Project Name: " + project.projName);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
    }
}

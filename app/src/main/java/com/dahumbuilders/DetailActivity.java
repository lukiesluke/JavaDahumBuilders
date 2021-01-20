package com.dahumbuilders;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.dahumbuilders.fragment.DetailFragment;
import com.dahumbuilders.model.Summary;
import com.google.gson.Gson;

public class DetailActivity extends AppCompatActivity {
    public static final String SUMMARY_KEY = "summary_key";
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView appBarTitle = findViewById(R.id.appBarTitle);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Summary summary = gson.fromJson(bundle.getString(SUMMARY_KEY), Summary.class);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.your_placeholder, DetailFragment.newInstance(bundle.getString(SUMMARY_KEY)));
            fragmentTransaction.commit();

            appBarTitle.setText(getString(R.string.detail_title, summary.datePaid));
            Log.d("lwg", "DetailActivity: " + summary.datePaid);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
    }
}
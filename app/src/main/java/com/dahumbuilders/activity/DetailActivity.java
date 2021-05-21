package com.dahumbuilders.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.dahumbuilders.R;
import com.dahumbuilders.Utils;
import com.dahumbuilders.fragment.DetailFragment;
import com.dahumbuilders.model.Summary;
import com.google.gson.Gson;

public class DetailActivity extends AppCompatActivity {
    public static final String KEY_SUMMARY = "key_summary";
    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView appBarTitle = findViewById(R.id.appBarTitle);
        appBarTitle.setTypeface(Utils.fontBold(getAssets()));
        appBarTitle.setText(getString(R.string.detail_title));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Summary summary = gson.fromJson(bundle.getString(KEY_SUMMARY), Summary.class);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.your_placeholder, DetailFragment.newInstance(bundle.getString(KEY_SUMMARY)));
            fragmentTransaction.commit();
            Log.d("lwg", "DetailActivity: " + summary.datePaid);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
    }
}
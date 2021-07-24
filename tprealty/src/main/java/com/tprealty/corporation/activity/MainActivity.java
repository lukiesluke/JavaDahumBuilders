package com.tprealty.corporation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.messaging.FirebaseMessaging;
import com.tprealty.corporation.R;
import com.tprealty.corporation.Utils;
import com.tprealty.corporation.adapter.MainViewPagerAdapter;
import com.tprealty.corporation.fragment.ProjectListFragment;
import com.tprealty.corporation.fragment.SummaryFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.tprealty.corporation.network.Constant.CHANNEL_NAME;
import static com.tprealty.corporation.network.Constant.EMPTY;
import static com.tprealty.corporation.network.Constant.PROJECT;
import static com.tprealty.corporation.network.Constant.SUMMARY;

public class MainActivity extends AppCompatActivity {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> stringList = new ArrayList<>();
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onNewIntent(getIntent());

        TextView appBarTitle = findViewById(R.id.appBarTitle);
        appBarTitle.setTypeface(Utils.fontBold(getAssets()));

        tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);

        MainViewPagerAdapter viewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), 0);

        fragmentList.add(SummaryFragment.newInstance());
        fragmentList.add(ProjectListFragment.newInstance(EMPTY, EMPTY));
        stringList.add(SUMMARY);
        stringList.add(PROJECT);

        viewPagerAdapter.addFragment(fragmentList, stringList);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
//                            Log.w("lwg", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();
//                        Log.w("lwg", "Hello toke: " + token);
//                        Toast.makeText(MainActivity.this, "Hello toke: " + token, Toast.LENGTH_LONG).show();
                    }
                });
        FirebaseMessaging.getInstance().subscribeToTopic(CHANNEL_NAME);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey(getString(R.string.key_notification))) {
                String msg = extras.getString(getString(R.string.key_notification));
                TabLayout.Tab tab;
                if (msg.contains(getString(R.string.project))) {
                    tab = tabLayout.getTabAt(1);
                } else {
                    tab = tabLayout.getTabAt(0);
                }
                Objects.requireNonNull(tab).select();
            }
        }
        super.onNewIntent(intent);
    }
}
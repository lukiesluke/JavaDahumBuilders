package com.tprealty.corporation.activity;

import static com.tprealty.corporation.network.Constant.CHANNEL_NAME;
import static com.tprealty.corporation.network.Constant.EMPTY;
import static com.tprealty.corporation.network.Constant.PROJECT;
import static com.tprealty.corporation.network.Constant.SUMMARY;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.messaging.FirebaseMessaging;
import com.tprealty.corporation.R;
import com.tprealty.corporation.adapter.MainViewPagerAdapter;
import com.tprealty.corporation.fragment.ProjectListFragment;
import com.tprealty.corporation.fragment.SummaryFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> stringList = new ArrayList<>();
    private TabLayout tabLayout;
    private TabLayout.Tab tab;
    private int tabIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onNewIntent(getIntent());

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setLogo(R.drawable.ic_tpcorp_24);

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

        tab = tabLayout.getTabAt(tabIndex);
        Objects.requireNonNull(tab).select();

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
                try {
                    //Get notification body onclick and set tabIndex select position.
                    String msg = extras.getString(getString(R.string.key_notification));
                    if (msg.contains(getString(R.string.project))) {
                        tabIndex = 1;
                    } else {
                        tabIndex = 0;
                    }
                    tab = tabLayout.getTabAt(tabIndex);
                    Objects.requireNonNull(tab).select();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        super.onNewIntent(intent);
    }
}
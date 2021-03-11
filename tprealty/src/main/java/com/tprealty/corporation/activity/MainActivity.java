package com.tprealty.corporation.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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

import static com.tprealty.corporation.network.Constant.CHANNEL_NAME;
import static com.tprealty.corporation.network.Constant.EMPTY;
import static com.tprealty.corporation.network.Constant.PROJECT;
import static com.tprealty.corporation.network.Constant.SUMMARY;

public class MainActivity extends AppCompatActivity {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> stringList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView appBarTitle = findViewById(R.id.appBarTitle);
        appBarTitle.setTypeface(Utils.fontBold(getAssets()));

        TabLayout tabLayout = findViewById(R.id.tabLayout);
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

                        // Log and toast
                        Log.w("lwg", "Hello toke: " + token);
                        Toast.makeText(MainActivity.this, "Hello toke: " + token, Toast.LENGTH_LONG).show();
                    }
                });

        FirebaseMessaging.getInstance().subscribeToTopic(CHANNEL_NAME);
    }
}
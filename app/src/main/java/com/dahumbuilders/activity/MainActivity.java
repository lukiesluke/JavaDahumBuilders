package com.dahumbuilders.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.dahumbuilders.R;
import com.dahumbuilders.Utils;
import com.dahumbuilders.adapter.MainViewPagerAdapter;
import com.dahumbuilders.fragment.ProjectListFragment;
import com.dahumbuilders.fragment.SummaryFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import static com.dahumbuilders.network.Constant.CHANNEL_DESCRIPTION;
import static com.dahumbuilders.network.Constant.CHANNEL_ID;
import static com.dahumbuilders.network.Constant.CHANNEL_NAME;
import static com.dahumbuilders.network.Constant.EMPTY;
import static com.dahumbuilders.network.Constant.PROJECT;
import static com.dahumbuilders.network.Constant.SUMMARY;

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
                            Log.w("lwg", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        Log.w("lwg", "Hello toke: " + token);
                        Toast.makeText(MainActivity.this, "Hello toke: " + token, Toast.LENGTH_LONG).show();
                    }
                });

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription(CHANNEL_DESCRIPTION);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setLightColor(Color.BLUE);

            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}

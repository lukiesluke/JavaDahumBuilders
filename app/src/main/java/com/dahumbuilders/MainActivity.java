package com.dahumbuilders;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.dahumbuilders.adapter.MainViewPagerAdapter;
import com.dahumbuilders.fragment.ProjectListFragment;
import com.dahumbuilders.fragment.SummaryFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);

        MainViewPagerAdapter viewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(SummaryFragment.newInstance("", ""), "Summary Report");
        viewPagerAdapter.addFragment(ProjectListFragment.newInstance("", ""), "Project List");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}

package com.dahumbuilders.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.dahumbuilders.R;
import com.dahumbuilders.adapter.MainViewPagerAdapter;
import com.dahumbuilders.fragment.ProjectListFragment;
import com.dahumbuilders.fragment.SummaryFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.dahumbuilders.network.Constant.EMPTY;
import static com.dahumbuilders.network.Constant.PROJECT;
import static com.dahumbuilders.network.Constant.SUMMARY;

public class MainActivity extends AppCompatActivity {
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> stringList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }
}

package com.example.endoscopycard.Ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.ViewGroup;

import com.example.endoscopycard.R;
import com.example.endoscopycard.Ui.fragments.fragment_details;
import com.example.endoscopycard.Ui.fragments.fragment_recommendations;
import com.example.endoscopycard.Ui.fragments.fragment_report;
import com.example.endoscopycard.Ui.fragments.fragment_scans;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
    ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_details,
            R.drawable.ic_report,
            R.drawable.ic_scans,
            R.drawable.ic_recommendations
    };
//    final int[] tabsUnselected = {
//            R.drawable.ic_music_dark,
//            R.drawable.ic_star_dark,
//            R.drawable.ic_chatbubbles_dark,
//            R.drawable.ic_settings_filled
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tabLayout=findViewById(R.id.tab_layout);
        viewPager=(ViewPager) findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(4);

        viewPagerAdapter.addFragment(new fragment_details());
        viewPagerAdapter.addFragment(new fragment_report());
        viewPagerAdapter.addFragment(new fragment_scans());
        viewPagerAdapter.addFragment(new fragment_recommendations());

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                tab.setIcon(tabsUnselected[tab.getPosition()]);
//                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                tab.setIcon(tabIcons[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);

    }
}

class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragments;
    int position_1;

    ViewPagerAdapter(FragmentManager fm){
        super(fm);
        this.fragments=new ArrayList<>();
    };

    @NonNull
    @Override
    public Fragment getItem(int position) {
        position_1=position;
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Yet another bug in FragmentStatePagerAdapter that destroyItem is called on fragment that hasnt been added. Need to catch
        try {
            super.destroyItem(container, position, object);
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }

    public void addFragment(Fragment fragment){
        fragments.add(fragment);
    }

}
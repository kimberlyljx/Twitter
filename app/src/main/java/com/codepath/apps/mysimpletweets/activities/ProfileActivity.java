package com.codepath.apps.mysimpletweets.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.adapters.SmartFragmentStatePagerAdapter;
import com.codepath.apps.mysimpletweets.adapters.TimelineFragmentPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {
    private SmartFragmentStatePagerAdapter adapterViewPager;

    @BindView(R.id.toolbar)
    Toolbar toolbar; // change toolbar
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;
    @BindView(R.id.flContent)
    FrameLayout frag;
    public static String POSITION = "POSITION";

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, tabLayout.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        viewPager.setCurrentItem(savedInstanceState.getInt(POSITION));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        adapterViewPager = new TimelineFragmentPagerAdapter(getSupportFragmentManager(),
                ProfileActivity.this);
        viewPager.setAdapter(adapterViewPager);

        // Give the TabLayout the ViewPager
        tabLayout.setupWithViewPager(viewPager);

        // Attach the page change listener inside the activity
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position);
                Toast.makeText(ProfileActivity.this,
                        "Selected page position: " + position, Toast.LENGTH_SHORT).show();
                showFragment();
            }
            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });
        viewPager.setCurrentItem(0);
        showFragment();
    }

    private void showFragment() {
        // 1. Get support fragment manager
        FragmentManager fm = getSupportFragmentManager();
        // 2. create a transaction
        FragmentTransaction ft = fm.beginTransaction();
        // 3. add/remove fragment
        ft.replace(R.id.flContent, adapterViewPager.getItem(viewPager.getCurrentItem()) );
//        ft.addToBackStack("two");
        // 4. commit the transaction
        ft.commit();
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE! Make sure to override the method with only a single `Bundle` argument
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
}

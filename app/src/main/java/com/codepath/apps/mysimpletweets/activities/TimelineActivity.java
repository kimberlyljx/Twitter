package com.codepath.apps.mysimpletweets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.adapters.SmartFragmentStatePagerAdapter;
import com.codepath.apps.mysimpletweets.adapters.TimelineFragmentPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimelineActivity extends AppCompatActivity {
    private SmartFragmentStatePagerAdapter adapterViewPager;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.nvView) NavigationView nvDrawer;
    @BindView(R.id.drawer_layout) DrawerLayout mDrawer;
    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.sliding_tabs) TabLayout tabLayout;

    private ActionBarDrawerToggle drawerToggle;
    private ImageView ivNavProfile;
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
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_hamburger);

        // Add at runtime b/c (null exceptions) on header lookups
        View headerLayout = nvDrawer.inflateHeaderView(R.layout.nav_header);
        ivNavProfile = (ImageView) headerLayout.findViewById(R.id.ivNavProfile);
        // Setup drawer view
        setupDrawerContent(nvDrawer);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        adapterViewPager = new TimelineFragmentPagerAdapter(getSupportFragmentManager(), TimelineActivity.this);
        viewPager.setAdapter(adapterViewPager);

        // Give the TabLayout the ViewPager
        tabLayout.setupWithViewPager(viewPager);

        // SHOULD THIS BE IN ONCREATE or ONCREATEVIEW
        // Attach the page change listener inside the activity
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position);
                Toast.makeText(TimelineActivity.this,
                        "Selected page position: " + position, Toast.LENGTH_SHORT).show();
//                showFragment();
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

        getSupportActionBar().setTitle( TimelineFragmentPagerAdapter.getTitle( viewPager.getCurrentItem()) );
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new activity to show based on nav item clicked
//        Fragment fragment = null;
//        Class activityClass;
        Intent i;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                i = new Intent(TimelineActivity.this, ProfileActivity.class);
                startActivity(i);
                break;
//            case R.id.nav_second_fragment:
//                fragmentClass = HighlightsFragment.class;
//                break;
//            case R.id.nav_third_fragment:
//                fragmentClass = ListsFragment.class;
//                break;
//            case R.id.nav_fourth_fragment:
//                fragmentClass = ConnectFragment.class;
//                break;
            default:
                i = new Intent(TimelineActivity.this, ProfileActivity.class);
                startActivity(i);
        }

//        try {
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // Insert the fragment by replacing any existing fragment
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
//
//        // Highlight the selected item has been done by NavigationView
//        menuItem.setChecked(true);
//        // Set action bar title
//        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE! Make sure to override the method with only a single `Bundle` argument
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    // REQUEST_CODE can be any value we like, used to determine the result type later
    private final int TWEET_CODE = 20;

    public void composeTweet(View view) {
        Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
        startActivityForResult(i, TWEET_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == TWEET_CODE) {
//            Tweet tweet = (Tweet) getIntent().getSerializableExtra("tweet");
//            HomeTimelineFragment fragmentHomeTweets =
//                    (HomeTimelineFragment) adapterViewPager.getRegisteredFragment(0);
//            fragmentHomeTweets.appendTweet(tweet);
//            viewPager.setCurrentItem(0);
//            Toast.makeText(this, "Tweeted", Toast.LENGTH_SHORT).show();

        }
    }
}

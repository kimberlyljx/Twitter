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
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.adapters.SmartFragmentStatePagerAdapter;
import com.codepath.apps.mysimpletweets.adapters.TimelineFragmentPagerAdapter;
import com.codepath.apps.mysimpletweets.models.User;

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
    private User user;
    public TwitterClient activityClient;

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

        activityClient = TwitterApplication.getRestClient();

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
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                Intent i = new Intent(TimelineActivity.this, ProfileActivity.class);
                i.putExtra("screen_name", (String) null);
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

    public void showProfile(View view) {
        User user = (User) view.getTag();
        Intent i = new Intent(TimelineActivity.this, ProfileActivity.class);
        i.putExtra("screen_name", user.getScreenName());
        startActivity(i);
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
//            Tweet tweet = (Tweet) getIntent().getSerializableExtra("tweet;
//            HomeTimelineFragment fragmentHomeTweet=
//                    (HomeTimelineFragment) adapterViewPager.getRegisteredFragment(;
//            fragmentHomeTweets.appendTweet(twee;
//            viewPager.setCurrentItem(;
//            Toast.makeText(this, "Tweeted", Toast.LENGTH_SHORT).show();

        }
    }

//    private void getUser(String screenName) {
//        // Now we can execute the long-running task at any time.
//        new MyAsyncTask().execute(screenName);
//    }
//
//    // The types specified here are the input data type, the progress type, and the result type
//    private class MyAsyncTask extends AsyncTask<String, Void, User> {
//        protected void onPreExecute() {
//            // Runs on the UI thread before doInBackground
//            // Good for toggling visibility of a progress indicator
////            progressBar.setVisibility(ProgressBar.VISIBLE);
//        }
//
//        protected User doInBackground(String... strings) {
//            // Some long-running task like downloading an image.
//            activityClient.getUserDetails(new JsonHttpResponseHandler() {
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                    Log.d("JSON", response.toString());
//                    user = User.fromJSON(response);
//                }
//                @Override
//                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                    Log.d("JSON", errorResponse.toString());
//                }
//            },"klimjinx");
//            return user;
//        }

//        protected void onProgressUpdate(Progress... values) {
//            // Executes whenever publishProgress is called from doInBackground
//            // Used to update the progress indicator
////            progressBar.setProgress(values[0]);
//        }

//        protected void onPostExecute(User result) {
            // This method is executed in the UIThread
            // with access to the result of the long running task
//            imageView.setImageBitmap(result);
            // Hide the progress bar
//            progressBar.setVisibility(ProgressBar.INVISIBLE);
//            Intent i = new Intent(TimelineActivity.this, ProfileActivity.class);
//            i.putExtra("user", user);
//            startActivity(i);
}
//    }
//}

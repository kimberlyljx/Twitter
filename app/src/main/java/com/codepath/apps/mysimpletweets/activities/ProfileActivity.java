package com.codepath.apps.mysimpletweets.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.adapters.ProfileFragmentPagerAdapter;
import com.codepath.apps.mysimpletweets.adapters.SmartFragmentStatePagerAdapter;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {
    private SmartFragmentStatePagerAdapter adapterViewPager;

    @BindView(R.id.toolbar)
    Toolbar toolbar; // change toolbar
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;

    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvLocation)
    TextView tvLocation;
    @BindView(R.id.tvUsername)
    TextView tvUsername;
    @BindView(R.id.tvWebsite)
    TextView tvWebsite;
    @BindView(R.id.tvFollowers)
    TextView tvFollowers;
    @BindView(R.id.tvFollowing)
    TextView tvFollowing;
    @BindView(R.id.ibProfile)
    ImageButton ibProfile;

    TwitterClient client;
    String screenName;
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

    // Loads either the current user OR a specified user's data
    public void loadUserInfo(String screenName) {
        if (screenName != null && !screenName.isEmpty()) {
            client.getUserDetails(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    User user = User.fromJSON(response);
                    Log.d("ProfileActivity", response.toString());
                    setProfile(user);
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("ProfileActivity", errorResponse.toString());
                }
            }, screenName);

        } else { // no screenName was passed
            // Trigger call to "account/verifyCredentials" endpoint
            // to load current user profile data
            // populate the top of the profile view
            client.getMyInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    User user = User.fromJSON (response);
                    Log.d("ProfileActivity", response.toString());
                    setProfile(user);
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("ProfileActivityPersonal", errorResponse.toString());
                }
            });
        }
    }

//    public void showProfile(View view) {
//        User user = (User) view.getTag();
//        loadUserInfo(user.getScreenName());
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = TwitterApplication.getRestClient();

        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        screenName = getIntent().getStringExtra("screen_name");
        loadUserInfo(screenName);

        adapterViewPager = new ProfileFragmentPagerAdapter(getSupportFragmentManager(), ProfileActivity.this, screenName);
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
        loadUserInfo(screenName);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
//        user = adapterViewPager.getRegisteredFragment(0).getCurrentU;
        return super.onCreateView(parent, name, context, attrs);
    }

    private void setProfile(User user) {
        tvUsername.setText(user.getScreenName());
        tvName.setText(user.getName());
        tvDescription.setText(user.getDescription());
        tvFollowers.setText( Integer.toString(user.getFollowersCount()) + " Followers" );
        tvFollowing.setText( Integer.toString(user.getFollowingCount()) + " Following");
        Picasso.with(this).load(user.getProfileImageUrl()).into(ibProfile);
        if (user.getLocation() != null) tvLocation.setText(user.getLocation());
    }

    private void showFragment() {
        // 1. Get support fragment manager
        FragmentManager fm = getSupportFragmentManager();
        // 2. create a transaction
        FragmentTransaction ft = fm.beginTransaction();
        // 3. add/remove fragment
//        ft.replace(R.id.flContent, adapterViewPager.getItem(viewPager.getCurrentItem()) );
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

//    // The types specified here are the input data type, the progress type, and the result type
//    private void getUser(String screenName) {
//        // Now we can execute the long-running task at any time.
//        new MyAsyncTask().execute(screenName);
//    }
//
//    // The types specified here are the input data type, the progress type, and the result type
//    private class MyAsyncTask extends AsyncTask<String, Void, Void> {
//        protected Void doInBackground(String... strings) {
//            // Some long-running task like downloading an image.
//            loadUserInfo(strings[0]);
//            return null;
//        }
//    }
}

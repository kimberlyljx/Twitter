package com.codepath.apps.mysimpletweets.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.codepath.apps.mysimpletweets.fragments.AllTweetsFragment;
import com.codepath.apps.mysimpletweets.fragments.TopTweetsFragment;
import com.codepath.apps.mysimpletweets.fragments.UserTimelineFragment;

/**
 * Created by klimjinx on 7/1/16.
 */
public class SearchFragmentPagerAdapter extends SmartFragmentStatePagerAdapter {
    public final static int TOP_TWEETS_TAB = 0;
    public final static int ALL_TWEETS_TAB = 1;

    public String query;

    final int PAGE_COUNT = 2;
    private static String tabTitles[] = new String[] { "Top Tweets", "All Tweets" };
    private Context context;

    public SearchFragmentPagerAdapter(FragmentManager fm, Context context, String query) {
        super(fm);
        this.context = context;
        this.query = query;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case TOP_TWEETS_TAB:
                return TopTweetsFragment.newInstance(0, query);
            case ALL_TWEETS_TAB:
                return AllTweetsFragment.newInstance(1, query);
            default:
                return UserTimelineFragment.newInstance(0, query);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}
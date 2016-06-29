package com.codepath.apps.mysimpletweets.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.codepath.apps.mysimpletweets.fragments.HomeTimelineFragment;

/**
 * Created by klimjinx on 6/28/16.
 */
public class ProfileFragmentPagerAdapter extends SmartFragmentStatePagerAdapter {
    public final static int TWEETS_TAB = 0;
    public final static int MEDIA_TAB = 1;
    public final static int LIKES_TAB = 2;

    final int PAGE_COUNT = 3;
    private static String tabTitles[] = new String[] { "Tweets", "Media", "Likes" };
    private Context context;

    public ProfileFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case TWEETS_TAB:
                return HomeTimelineFragment.newInstance(0);
//            case MEDIA_TAB:
//                return MediaFragment.newInstance(1);
//            case LIKES_TAB:
//                return LikesFragment.newInstance(2);
            default:
                return HomeTimelineFragment.newInstance(0);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}

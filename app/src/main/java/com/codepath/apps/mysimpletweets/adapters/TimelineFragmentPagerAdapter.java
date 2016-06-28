package com.codepath.apps.mysimpletweets.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.codepath.apps.mysimpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.MentionsTimelineFragment;

/**
 * Created by klimjinx on 6/27/16.
 */
public class TimelineFragmentPagerAdapter extends SmartFragmentStatePagerAdapter {

    public final static int HOME_TAB = 0;
    public final static int MENTIONS_TAB = 1;
    public final static int MOMENTS_TAB = 2;
    public final static int NOTIFICATIONS_TAB = 3;
    public final static int MESSAGES_TAB = 4;

    final int PAGE_COUNT = 5; // 5 tabs
    private String tabTitles[] = new String[] { "Home", "Mentions", "Moments", "Notifications", "Messages" };
    private Context context;

    public TimelineFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    // What is diff btwn this and above?
//    public TimelineFragmentPagerAdapter(FragmentManager fragmentManager) {
//        super(fragmentManager);     }

    // Returns total number of pages
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case HOME_TAB:
                return HomeTimelineFragment.newInstance(0);
            case MENTIONS_TAB:
                return MentionsTimelineFragment.newInstance(1);
//            case MOMENTS_TAB:
//                return MomentsTimelineFragment.newInstance(2);
//            case NOTIFICATIONS_TAB:
//                return NotificationsFragment.newInstance(3);
//            case MESSAGES_TAB:
//                return MessagesFragment.newInstance(4);
            default:
                return null;
        }
    }


    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}

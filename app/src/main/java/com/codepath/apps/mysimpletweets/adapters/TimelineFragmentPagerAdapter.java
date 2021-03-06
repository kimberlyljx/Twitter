package com.codepath.apps.mysimpletweets.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.MentionsTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.MessagesFragment;

/**
 * Created by klimjinx on 6/27/16.
 */
public class TimelineFragmentPagerAdapter extends SmartFragmentStatePagerAdapter {

    public final static int HOME_TAB = 0;
    public final static int MENTIONS_TAB = 1;
    public final static int MOMENTS_TAB = 2;
    public final static int NOTIFICATIONS_TAB = 3;
    public final static int MESSAGES_TAB = 4;

    private int[] imageResId = {
            R.drawable.ic_home,
            R.drawable.ic_mentions,
            R.drawable.ic_lightning,
            R.drawable.ic_notifications,
            R.drawable.ic_messages,
    };

    final int PAGE_COUNT = 5; // 5 tabs
    private static String tabTitles[] = new String[] { "Home", "Mentions", "Moments", "Notifications", "Message" };
    private Context context;

    public TimelineFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

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
            case MESSAGES_TAB:
                return MessagesFragment.newInstance(4);
            default:
                return HomeTimelineFragment.newInstance(0);
        }
    }


    @Override
    public CharSequence getPageTitle(int position) {
//         Generate title based on item position
        Drawable image = ContextCompat.getDrawable(context, imageResId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;

    }

    public static String getTitle(int position) {
        return tabTitles[position];
    }
}

package com.codepath.apps.mysimpletweets.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by klimjinx on 6/27/16.
 */


// view holder pattern for every item;


public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1 , tweets);
    }

    // override and setup custom template


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }

        ImageView ivProfile = (ImageView) convertView.findViewById(R.id.ivProfile);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);

        tvUsername.setText(tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        ivProfile.setImageResource(0);

        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfile);

        return convertView;
    }
}

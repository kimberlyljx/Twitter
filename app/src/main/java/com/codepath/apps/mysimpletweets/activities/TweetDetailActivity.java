package com.codepath.apps.mysimpletweets.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.ParseRelativeDate;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TweetDetailActivity extends AppCompatActivity {
    @BindView(R.id.toolbar) Toolbar idToolbar;
    @BindView(R.id.ibProfile) ImageView ibProfile;
    @BindView(R.id.ivTweetMedia) ImageView ivTweetMedia;
    @BindView(R.id.tvName) TextView tvName;
    @BindView(R.id.tvUsername) TextView tvUsername;
    @BindView(R.id.tvBody) TextView tvBody;
    @BindView(R.id.tvTimestamp) TextView tvTimestamp;
    @BindView(R.id.tvCount) TextView tvCount;

    private Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);
        tweet = (Tweet) getIntent().getSerializableExtra("tweet");
        ButterKnife.bind(this);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/GothamNarrow-Book.otf");
        tvUsername.setTypeface(font);
        tvBody.setTypeface(font);
        tvTimestamp.setTypeface(font);

        Typeface boldFont = Typeface.createFromAsset(getAssets(), "fonts/GothamNarrow-Bold.otf");
        tvName.setTypeface(boldFont);

        tvUsername.setText("@" + tweet.getUser().getScreenName());
        tvName.setText(tweet.getUser().getName());
        String time = ParseRelativeDate.getRelativeTimeAgo(tweet.getCreatedAt());
        tvTimestamp.setText(parseString(time));
        tvBody.setText(tweet.getBody());
        ibProfile.setTag(tweet.getUser());
        ibProfile.setImageResource(0);
        Picasso.with(ibProfile.getContext() )
                .load(tweet.getUser().getProfileImageUrl()).into(ibProfile);

        if (tweet.getMainMediaUrl() != null) {
            Picasso.with(ivTweetMedia.getContext()).load(tweet.getMainMediaUrl()).into(ivTweetMedia);
        }

        if (tweet.getFavorited()) {
            int imgResource = R.drawable.ic_red_heart;
            viewHolder.ibLike.setCompoundDrawablesWithIntrinsicBounds(imgResource, 0, 0, 0);
        }

        if (tweet.getFavoritesCount() != 0) viewHolder.ibLike.setText(Integer.toString(tweet.getFavoritesCount()));


        if (tweet.getRetweeted()) {
            int imgResource = R.drawable.ic_green_retweet;
            viewHolder.ibRetweet.setCompoundDrawablesWithIntrinsicBounds(imgResource, 0, 0, 0);
        }
        if (tweet.getFavoritesCount() != 0) viewHolder.ibRetweet.setText(Integer.toString(tweet.getRetweetCount()));

    }


    public String parseString(String timestamp) {
        timestamp = timestamp.replace("Yesterday", "1d");
        timestamp = timestamp.replace(" minutes ago", "m");
        timestamp = timestamp.replace(" hours ago", "h");
        timestamp = timestamp.replace(" days ago", "d");
        timestamp = timestamp.replace(" seconds ago", "s");
        return timestamp;
    }

}

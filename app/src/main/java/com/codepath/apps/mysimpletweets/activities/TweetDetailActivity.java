package com.codepath.apps.mysimpletweets.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
    @BindView(R.id.ibReply) Button ibReply;
    @BindView(R.id.ibRetweet)Button ibRetweet;
    @BindView(R.id.ibLike)Button ibLike;

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
        tvTimestamp.setText(time);
        tvBody.setText(tweet.getBody());
        ibProfile.setTag(tweet.getUser());
        ibProfile.setImageResource(0);
        Picasso.with(ibProfile.getContext() )
                .load(tweet.getUser().getProfileImageUrl()).into(ibProfile);

        Integer retweetCount = tweet.getRetweetCount();
        Integer favouritesCount = tweet.getFavoritesCount();

        String count = Integer.toString(retweetCount) + " RETWEETS    " +  Integer.toString(favouritesCount) + " LIKES";

        tvCount.setText(count);
        ivTweetMedia.setImageResource(0);
        if (tweet.getMainMediaUrl() != null) {
            Glide.with(ivTweetMedia.getContext()).load(tweet.getMainMediaUrl()).into(ivTweetMedia);
        }
        if (tweet.getFavorited()) {
            int imgResource = R.drawable.ic_red_heart;
            ibLike.setCompoundDrawablesWithIntrinsicBounds(imgResource, 0, 0, 0);
        }
        if (tweet.getFavoritesCount() != 0)
            ibLike.setText(Integer.toString(tweet.getFavoritesCount()));
        if (tweet.getRetweeted()) {
            int imgResource = R.drawable.ic_green_retweet;
            ibRetweet.setCompoundDrawablesWithIntrinsicBounds(imgResource, 0, 0, 0);
        }
        if (tweet.getRetweetCount() != 0) ibRetweet.setText(Integer.toString(tweet.getRetweetCount()));

    }

}

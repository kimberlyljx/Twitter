package com.codepath.apps.mysimpletweets.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.ParseRelativeDate;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.activities.ComposeActivity;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by klimjinx on 6/27/16.
 */


// view holder pattern for every item;


public class TweetsArrayAdapter extends RecyclerView.Adapter<TweetsArrayAdapter.ViewHolder> {

    // Store a member variable for the contacts
    private ArrayList<Tweet> amTweets;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public TweetsArrayAdapter(Context context, ArrayList<Tweet> tweets) {
        this.amTweets = tweets;
        this.mContext = context;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        private ImageView ivProfile;
        private TextView tvName;
        private TextView tvUsername;
        private TextView tvBody;
        private TextView tvTimestamp;
        private ImageButton ibReply;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            ivProfile = (ImageView) itemView.findViewById(R.id.ibProfile);
            ibReply = (ImageButton) itemView.findViewById(R.id.ibReply);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvTimestamp = (TextView) itemView.findViewById(R.id.tvTimestamp);

        }

        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            int position = getLayoutPosition(); // gets item position
            Tweet tweet = amTweets.get(position);
//            Toast.makeText(view.getContext(), tweet.getBody(), Toast.LENGTH_SHORT).show();
        }
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public TweetsArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_tweet, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(TweetsArrayAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        final Tweet tweet = amTweets.get(position);

        ImageButton ibReply = viewHolder.ibReply;
        ibReply.setTag(tweet);
        ibReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TwitterClient client = TwitterApplication.getRestClient();
                client.getMyInfo(new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        User user = User.fromJSON (response);
                        Log.d("Reply", response.toString());
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.d("Reply", errorResponse.toString());
                    }
                });
                Toast.makeText(mContext, "Reply ", Toast.LENGTH_SHORT).show();

                Intent i = new Intent( mContext, ComposeActivity.class);
                i.putExtra("tweetID", tweet.getUid() );
                i.putExtra("tweetUser", tweet.getUser().getScreenName() );
                mContext.startActivity(i);
            }
        });

        // Set item views based on your views and data model
        TextView tvUsername = viewHolder.tvUsername;
        tvUsername.setText("@" + tweet.getUser().getScreenName());

        TextView tvName = viewHolder.tvName;
        tvName.setText(tweet.getUser().getName());

        TextView tvTimestamp = viewHolder.tvTimestamp;
        String time = ParseRelativeDate.getRelativeTimeAgo(tweet.getCreatedAt());
        tvTimestamp.setText(parseString(time));

        TextView tvBody = viewHolder.tvBody;
        tvBody.setText(tweet.getBody());

        ImageView ivProfile = viewHolder.ivProfile;
        ivProfile.setTag(tweet.getUser());

        ivProfile.setImageResource(0);
        Picasso.with( ivProfile.getContext() )
                .load(tweet.getUser().getProfileImageUrl()).into(ivProfile);

    }

    public String parseString(String timestamp) {
        timestamp = timestamp.replace("Yesterday", "1d");
        timestamp = timestamp.replace(" minutes ago", "m");
        timestamp = timestamp.replace(" hours ago", "h");
        timestamp = timestamp.replace(" days ago", "d");
        timestamp = timestamp.replace(" seconds ago", "s");
        return timestamp;
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return amTweets.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        amTweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<Tweet> list) {
        amTweets.addAll(list);
        notifyDataSetChanged();
    }
}

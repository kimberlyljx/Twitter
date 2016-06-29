package com.codepath.apps.mysimpletweets.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.ParseRelativeDate;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by klimjinx on 6/27/16.
 */


// view holder pattern for every item;


public class TweetsArrayAdapter extends RecyclerView.Adapter<TweetsArrayAdapter.ViewHolder> {

    // Store a member variable for the contacts
    private List<Tweet> mTweets;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        this.mTweets = tweets;
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

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            ivProfile = (ImageView) itemView.findViewById(R.id.ivProfile);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvTimestamp = (TextView) itemView.findViewById(R.id.tvTimestamp);
        }

        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            int position = getLayoutPosition(); // gets item position
            Tweet tweet = mTweets.get(position);
            Toast.makeText(view.getContext(), tweet.getBody(), Toast.LENGTH_SHORT).show();

//            // Create an intent to display article
//            Intent intent = new Intent(context, ArticleActivity.class);
//            // pass in that article to intent
//            intent.putExtra("article", article);
//            //launch the activity
//            context.startActivity(intent);
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
        Tweet tweet = mTweets.get(position);

        // Set item views based on your views and data model
        TextView tvUsername = viewHolder.tvUsername;
        tvUsername.setText(tweet.getUser().getScreenName());

        TextView tvName = viewHolder.tvName;
        tvName.setText(tweet.getUser().getName());

        TextView tvTimestamp = viewHolder.tvTimestamp;
        tvTimestamp.setText(ParseRelativeDate.getRelativeTimeAgo(tweet.getCreatedAt()));

        TextView tvBody = viewHolder.tvBody;
        tvBody.setText(tweet.getBody());

        ImageView ivProfile = viewHolder.ivProfile;
        ivProfile.setImageResource(0);
        Picasso.with( ivProfile.getContext() ).load(tweet.getUser().getProfileImageUrl()).into(ivProfile);

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mTweets.size();
    }

}

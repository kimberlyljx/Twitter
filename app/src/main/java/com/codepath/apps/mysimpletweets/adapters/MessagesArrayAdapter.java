package com.codepath.apps.mysimpletweets.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.ParseRelativeDate;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Message;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by klimjinx on 6/30/16.
 */
public class MessagesArrayAdapter extends RecyclerView.Adapter<MessagesArrayAdapter.ViewHolder> {

    // Store a member variable for the contacts
    private ArrayList<Message> amMessages;
    // Store the context for easy access
    private Context mContext;

    private Typeface font;
    private Typeface boldFont;

    // Pass in the contact array into the constructor
    public MessagesArrayAdapter(Context context, ArrayList<Message> messages) {
        this.amMessages = messages;
        this.mContext = context;
        font = Typeface.createFromAsset(context.getAssets(), "fonts/GothamNarrow-Book.otf");
        boldFont = Typeface.createFromAsset(context.getAssets(), "fonts/GothamNarrow-Bold.otf");
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
            ivProfile = (ImageView) itemView.findViewById(R.id.ibProfile);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvTimestamp = (TextView) itemView.findViewById(R.id.tvTimestamp);
            tvUsername.setTypeface(font);
            tvBody.setTypeface(font);
            tvTimestamp.setTypeface(font);
            tvName.setTypeface(boldFont);
        }

        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            int position = getLayoutPosition(); // gets item position
            Message message  = amMessages.get(position);
//            Toast.makeText(view.getContext(), message.getBody(), Toast.LENGTH_SHORT).show();
        }
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public MessagesArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_message, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(MessagesArrayAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Message message  = amMessages.get(position);

        // Set item views based on your views and data model
        TextView tvUsername = viewHolder.tvUsername;
        tvUsername.setText("@" + message.getSender().getScreenName());

        TextView tvName = viewHolder.tvName;
        tvName.setText(message.getSender().getName());

        TextView tvTimestamp = viewHolder.tvTimestamp;
        String time = ParseRelativeDate.getRelativeTimeAgo(message.getCreatedAt());
        tvTimestamp.setText(parseString(time));

        TextView tvBody = viewHolder.tvBody;
        tvBody.setText(message.getBody());

        ImageView ivProfile = viewHolder.ivProfile;
        ivProfile.setTag(message.getSender());

        ivProfile.setImageResource(0);
        Picasso.with( ivProfile.getContext() )
                .load(message.getSender().getProfileImageUrl()).into(ivProfile);

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
        return amMessages.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        amMessages.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<Message> list) {
        amMessages.addAll(list);
        notifyDataSetChanged();
    }
}

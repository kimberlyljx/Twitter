package com.codepath.apps.mysimpletweets.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.ItemClickSupport;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.activities.TweetDetailActivity;
import com.codepath.apps.mysimpletweets.adapters.MessagesArrayAdapter;
import com.codepath.apps.mysimpletweets.models.Message;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Created by klimjinx on 6/27/16.
 */
public class MessagesFragment extends Fragment {
    public TwitterClient client;
    ArrayList<Message> mMessages;
    MessagesArrayAdapter aMessages;
    @BindView(R.id.rvMessages)
    RecyclerView rvMessages;
    public SwipeRefreshLayout swipeContainer;

    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;

    public static MessagesFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        MessagesFragment fragment = new MessagesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMessages = new ArrayList<>();
        aMessages = new MessagesArrayAdapter(getActivity(), mMessages);
        client = TwitterApplication.getRestClient();
        populateMessages();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(this, view);
        ItemClickSupport.addTo(rvMessages).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Toast.makeText(getContext(), "Clicked " + Integer.toString(position), Toast.LENGTH_SHORT).show();
                        Log.d("Message", "cool");
                        Intent i = new Intent( getActivity() , TweetDetailActivity.class);
                        i.putExtra("message", mMessages.get(position));
                        startActivity(i);
                    }
                }
        );

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimelineAsync(0);
            }
        });
        rvMessages.setAdapter(aMessages);
        rvMessages.setLayoutManager(new LinearLayoutManager(getContext()));
        mPage = getArguments().getInt(ARG_PAGE);
        return view;
    }


    // [] at the root is a jsonOBject
    private void populateMessages() {
        client.getDirectMessages(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // Deserialize Json and load model date into listview'
                Log.d("JSON", response.toString());
                mMessages.addAll(Message.fromJSONArray(response));
                aMessages.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("JSON", errorResponse.toString());
            }
        });
    }

    public void fetchTimelineAsync(int page) {
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        client.getDirectMessages(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // Remember to CLEAR OUT old items before appending in the new ones
                aMessages.clear();
                // ...the data has come back, add new items to your adapter...
                mMessages.addAll(Message.fromJSONArray(response));
                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("DEBUG", "Fetch timeline error: " + errorResponse.toString());
            }
        });
    }
    public void appendMessage(Message message) {
        mMessages.add(0, message);
        aMessages.notifyItemInserted(0);
        rvMessages.scrollToPosition(0);
    }
}

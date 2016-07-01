package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TopTweetsFragment extends TweetsListFragment {
    public TwitterClient client;
    private boolean popular;
    private String query;

    public static TopTweetsFragment newInstance(int page, String query) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString("query", query);
        TopTweetsFragment fragment = new TopTweetsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        this.popular = true;
        this.query = getArguments().getString("query");
        searchForTweets();
    }

    // [] at the root is a jsonOBject
    public void searchForTweets() {
        client.getSearchTweets(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Deserialize Json and load model date into listview'
                Log.d("JSON", response.toString());
                try {
                    mTweets.addAll(Tweet.fromJSONArray(response.getJSONArray("statuses")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                aTweets.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("JSON", errorResponse.toString());
            }
        }, this.query, popular);
    }
}

package com.codepath.apps.mysimpletweets.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.fragments.SearchTweetsFragment;

public class SearchActivity extends AppCompatActivity {
    SearchTweetsFragment searchTweetsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if (savedInstanceState == null) {
            searchTweetsFragment = (SearchTweetsFragment)
                    getSupportFragmentManager().findFragmentById(R.id.searchFragment);
        }
        String query = getIntent().getStringExtra("query");
        savedInstanceState.putString("query", query);
    }

}

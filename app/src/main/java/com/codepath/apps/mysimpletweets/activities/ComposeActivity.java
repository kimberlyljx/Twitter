package com.codepath.apps.mysimpletweets.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;


// Referred to http://code.tutsplus.com/tutorials/creating-a-twitter-client-for-android-tweeting-retweeting-and-replying--pre-30666

public class ComposeActivity extends AppCompatActivity {

    TwitterClient client;

    private long tweetID = 0;
    /**the username for the tweet if it is a reply*/
    private String tweetName = "";

    @BindView(R.id.etTweet) EditText etTweet;
    @BindView(R.id.btnSend) Button btnSend;
    @BindView(R.id.ibProfile) ImageButton ibProfile;
    @BindView(R.id.ibCancel) ImageButton ibCancel;
    @BindView(R.id.ibLocation) ImageButton ibLocation;
    @BindView(R.id.ibCamera) ImageButton ibCamera;
    @BindView(R.id.ibGif) ImageButton ibGif;
    @BindView(R.id.ibPoll) ImageButton ibPoll;
    @BindView(R.id.tvNumChar) TextView tvNumChar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        ButterKnife.bind(this);
        client = TwitterApplication.getRestClient();

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/GothamNarrow-Book.otf");
        etTweet.setTypeface(font);
        tvNumChar.setTypeface(font);

        etTweet.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    tvNumChar.setText( Integer.toString(140 - s.toString().length()) );
                } else {
                    tvNumChar.setText(Integer.toString(140));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int aft) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setupTweet();
    }

    // get ready to tweet or sets up for replies
    // Sets up twitter and onClick listeners
    private void setupTweet() {
        //get any data passed to this intent for a reply
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //get the ID of the tweet we are replying to
            tweetID = extras.getLong("tweetID");
            //get the user screen name for the tweet we are replying to
            tweetName = extras.getString("tweetUser");
            //use the passed information
            //get a reference to the text field for tweeting
            EditText etTweet = (EditText)findViewById(R.id.etTweet);
            //start the tweet text for the reply @username
            etTweet.setText("@"+ tweetName + " ");
            //set the cursor to the end of the text for entry
            etTweet.setSelection(etTweet.getText().length());
        } else {
            EditText etTweet = (EditText)findViewById(R.id.etTweet);
            etTweet.setText("");
        }
    }

//    public void onClick(View view) {
//        //handle home and send button clicks
//        //find out which view has been clicked
//        switch (view.getId()) {
//            case R.id.btnSend:
//                //send tweet
//                String toTweet = etTweet.getText().toString();
//
//                //handle replies
//                if( tweetName.length() > 0) {
////                    client.postStatusUpdate( new StatusUpdate(toTweet).inReplyToStatusId(tweetID) );
//                } else {
//                    //handle normal tweets
//                    client.postStatusUpdate( new JsonHttpResponseHandler() {
//                        @Override
//                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                            // Deserialize Json and load model date into listview'
//                            Log.d("JSON", response.toString());
//                            Tweet tweet = Tweet.fromJSON(response);
//                            Intent data = new Intent();
//                            data.putExtra("tweet", Parcels.wrap(tweet));
//                            setResult(RESULT_OK, data); // set result code and bundle data for response
//                            finish(); // closes the activity, pass data to parent
//                        }
//
//                        @Override
//                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                            Log.d("JSON", errorResponse.toString());
//                        }
//                    } , toTweet);
//                }
//
//                //reset the edit text
//                etTweet.setText("");
//                break;
//            default:
//                break;
//        }
//    }

    public void cancelTweet(View view) {
        finish();
    }

    public void postTweet(View view) {
        //send tweet
        String toTweet = etTweet.getText().toString();

        //handle replies
        if( tweetName.length() > 0) {
//                    client.postStatusUpdate( new StatusUpdate(toTweet).inReplyToStatusId(tweetID) );
            //handle normal tweets
            client.postReply ( new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // Deserialize Json and load model date into listview'
                    Log.d("JSON", response.toString());
                    //reset the edit text
                    etTweet.setText("");
                    Tweet tweet = Tweet.fromJSON(response);
                    Intent data = new Intent();
                    data.putExtra("tweet", tweet);
                    setResult(RESULT_OK, data); // set result code and bundle data for response
                    Log.d("JSON", response.toString());
                    finish(); // closes the activity, pass data to parent
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }

            } , toTweet, tweetID);
        } else {
            //handle normal tweets
            client.postStatusUpdate( new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // Deserialize Json and load model date into listview'
                    Log.d("JSON", response.toString());
                    //reset the edit text
                    etTweet.setText("");
                    Tweet tweet = Tweet.fromJSON(response);
                    Intent data = new Intent();
                    data.putExtra("tweet", tweet);
                    setResult(RESULT_OK, data); // set result code and bundle data for response
                    Log.d("JSON", response.toString());
                    finish(); // closes the activity, pass data to parent
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }

            } , toTweet);
        }
    }
}


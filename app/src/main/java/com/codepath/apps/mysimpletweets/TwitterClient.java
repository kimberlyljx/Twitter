package com.codepath.apps.mysimpletweets;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {

    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "CBT5YPgCqfLMD1HpVMMuK5jNm";       // Change this
	public static final String REST_CONSUMER_SECRET = "pUp6bDu0kQbmFdcxXNewAjPd4O8Pmos1edG8FRiWFEugPS7FwR"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://klimjinx-twitter";

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here

    // Get user profile based on username
    public void getUserDetails(AsyncHttpResponseHandler handler, String username) {
        String apiUrl = getApiUrl("users/show.json");
        RequestParams params = new RequestParams();
        params.put("screen_name", username);
        getClient().get(apiUrl, params, handler);
    }

    public void getUserTimeline(AsyncHttpResponseHandler handler, String username) {
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        RequestParams params = new RequestParams();
        params.put("screen_name", username);
        getClient().get(apiUrl, params, handler);
    }

    public void getMyInfo(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("account/verify_credentials.json");
        getClient().get(apiUrl, handler);
    }

    // Get hometimeline based on count and since when by id
    public void getHomeTimeline(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 20);
        getClient().get(apiUrl, params, handler);
    }

    public void getMentionsTimeline(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 10);
        getClient().get(apiUrl, params, handler);
    }

    public void getMomentsTimeline(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 10);
        getClient().get(apiUrl, params, handler);
    }

    public void getSearchTweets(AsyncHttpResponseHandler handler, String query, boolean popular) {
        String apiUrl = getApiUrl("search/tweets.json");
        RequestParams params = new RequestParams();
        params.put("q", query);
        if (popular) {params.put("result_type", "popular");}
        getClient().get(apiUrl, params, handler);
    }

    public void getDirectMessages(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("direct_messages.json");
        getClient().get(apiUrl, handler);
    }

    public void postStatusUpdate(AsyncHttpResponseHandler handler, String text) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", text);
        getClient().post(apiUrl, params, handler);
    }

    public void postReply(AsyncHttpResponseHandler handler, String text, long replyToId) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", text);
        params.put("in_reply_to_status_id", replyToId);
        getClient().post(apiUrl, params, handler);
    }

    public void postRetweet(AsyncHttpResponseHandler handler, long id) {
        String apiUrl = getApiUrl("statuses/retweet.json");
        RequestParams params = new RequestParams();
        params.put("id", id);
        params.put("trim_user", true);
        getClient().post(apiUrl, params, handler);
    }

    public void postUnretweet(AsyncHttpResponseHandler handler, long id) {
        String apiUrl = getApiUrl("statuses/unretweet.json");
        RequestParams params = new RequestParams();
        params.put("id", id);
        params.put("trim_user", true);
        getClient().post(apiUrl, params, handler);
    }

    public void postFavorite(AsyncHttpResponseHandler handler, long id) {
        String apiUrl = getApiUrl("favorites/create.json");
        RequestParams params = new RequestParams();
        params.put("id", id);
        getClient().post(apiUrl, params, handler);
    }

    public void destroyFavorite(AsyncHttpResponseHandler handler, long id) {
        String apiUrl = getApiUrl("favorites/destroy.json");
        RequestParams params = new RequestParams();
        params.put("id", id);
        getClient().post(apiUrl, params, handler);
    }

    public void getFavorites(AsyncHttpResponseHandler handler, String otherUserName) {
        String apiUrl = getApiUrl("favorites/list.json");
        RequestParams params = new RequestParams();
        if (otherUserName != null) {
            params.put("screen_name", otherUserName);
            getClient().get(apiUrl, params, handler);
        } else {
            getClient().get(apiUrl, handler);
        }
    }

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}
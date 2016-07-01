package com.codepath.apps.mysimpletweets.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table(name = "Tweets")
public class Tweet extends Model implements Serializable {

    // This is a regular field
    @Column(name = "Body")
    public String body;

    @Column(name = "uid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long uid;

    // This is an association to another activeandroid model
    @Column(name = "User", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    public User user;

    @Column(name = "created_at")
    String createdAt;

    @Column(name = "favorites_count")
    Integer favoritesCount;

    @Column(name = "retweet_count")
    Integer retweetCount;

    public Tweet() {
        super();
    }

    public User getUser() {
        return user;
    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
            tweet.favoritesCount = jsonObject.getInt("favorites_count");
            tweet.retweetCount = jsonObject.getInt("retweet_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tweet;
    }

    public static ArrayList<Tweet> fromJSONArray(JSONArray response) {
        ArrayList<Tweet> tweets = new ArrayList<>();

        for (int i = 0; i < response.length(); i++ ) {
            JSONObject tweetJson = null;
            try {
                tweetJson = response.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if (tweet != null) {
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return tweets;
    }

    public static List<Tweet> getAll(User user) {
        // This is how you execute a query
        return new Select()
                .from(User.class)
                .where("User = ?", user.getId())
                .orderBy("Name ASC")
                .execute();
    }

}
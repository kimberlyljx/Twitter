package com.codepath.apps.mysimpletweets.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by klimjinx on 6/27/16.
 */

@Table(name = "Users")
public class User extends Model implements Serializable {
    @Column(name = "Name")
    String name;

    @Column(name = "uid", unique = true)
    long uid;

    @Column(name = "screen_name")
    String screenName;

    @Column(name = "profile_image_url")
    String profileImageUrl;

    @Column(name = "Description")
    String description;

    @Column(name = "Location")
    String location;

    @Column(name = "followers_count")
    int followersCount;

    @Column(name = "following_count")
    int followingCount;

    // Make sure to have a default constructor for every ActiveAndroid model
    public User() {
        super();
    }

    // Used to return items from another table based on the foreign key
    public List<Tweet> tweets() {
        return getMany(Tweet.class, "Tweet");
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public static User fromJSON (JSONObject jsonObject) {
        User user = new User();
        try {
            user.name = jsonObject.getString("name");
            user.uid = jsonObject.getLong("id");
            user.screenName = jsonObject.getString("screen_name");
            user.profileImageUrl = jsonObject.getString("profile_image_url").replace("_normal", "");
            user.followersCount = jsonObject.getInt("followers_count");
            user.followingCount = jsonObject.getInt("friends_count");
            user.location = jsonObject.getString("location");
            user.description = jsonObject.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}

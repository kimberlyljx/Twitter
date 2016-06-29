package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by klimjinx on 6/27/16.
 */

public class User implements Serializable {
    String name;
    long uid;
    String screenName;
    String profileImageUrl;
    String description;
    String location;
    int followersCount;

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public int getFollowersCount() {
        return followersCount;
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
            user.profileImageUrl = jsonObject.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}

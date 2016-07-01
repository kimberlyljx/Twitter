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

@Table(name = "Message")
public class Message extends Model implements Serializable {

    // This is a regular field
    @Column(name = "Body")
    public String body;

    @Column(name = "uid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long uid;

    // This is an association to another activeandroid model
    @Column(name = "sender", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    public User sender;

    // This is an association to another activeandroid model
    @Column(name = "recipient", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    public User recipient;

    @Column(name = "created_at")
    String createdAt;

    public Message() {
        super();
    }

    public User getSender() {
        return sender;
    }

    public User getRecipient() {
        return recipient;
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

    public static Message fromJSON(JSONObject jsonObject) {
        Message message = new Message();
        try {
            message.body = jsonObject.getString("text");
            message.uid = jsonObject.getLong("id");
            message.createdAt = jsonObject.getString("created_at");
            message.sender = User.fromJSON(jsonObject.getJSONObject("sender"));
            message.recipient = User.fromJSON(jsonObject.getJSONObject("recipient"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return message;
    }

    public static ArrayList<Message> fromJSONArray(JSONArray response) {
        ArrayList<Message> messages = new ArrayList<>();

        for (int i = 0; i < response.length(); i++ ) {
            JSONObject messageJson = null;
            try {
                messageJson = response.getJSONObject(i);
                Message message = Message.fromJSON(messageJson);
                if (message != null) {
                    messages.add(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return messages;
    }

    public static List<Message> getAll(User user) {
        // This is how you execute a query
        return new Select()
                .from(User.class)
                .where("User = ?", user.getId())
                .orderBy("Name ASC")
                .execute();
    }

}
package tweet.com.mytweet.models;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Random;

import tweet.com.mytweet.app.MyTweetApp;

/**
 * Created by keela on 01/11/2017.
 */

public class Tweet {
    public Long id;
    public Long date;
    private String tweetMessage;
    private Long userId;

    private static final String JSON_ID  = "id";
    private static final String JSON_TWEET_MESSAGE   = "tweetmessage";
    private static final String JSON_DATE  = "date";
    private static final String JSON_USER_ID  = "userid";

    public Tweet(Long userId)
    {
        id = unsignedLong();
        date = new Date().getTime();
        tweetMessage = "";
        this.userId = userId;
    }

    public Tweet(JSONObject json) throws JSONException
    {
        id  = json.getLong(JSON_ID);
        tweetMessage  = json.getString(JSON_TWEET_MESSAGE);
        date = json.getLong(JSON_DATE);
        userId = json.getLong(JSON_USER_ID);
    }

    public JSONObject toJSON() throws JSONException
    {
        JSONObject json = new JSONObject();
        json.put(JSON_ID  , Long.toString(id));
        json.put(JSON_TWEET_MESSAGE  , tweetMessage);
        json.put(JSON_DATE  , date);
        json.put(JSON_USER_ID  , Long.toString(userId));
        return json;
    }

    /**
     * Src: Android labs - https://wit-ictskills-2017.github.io/mobile-app-dev/topic03-a/book-c-myrent-01%20(Widgets)/index.html#/05
     * Generate a long greater than zero
     * @return Unsigned Long value greater than zero
     */
    private Long unsignedLong() {
        long rndVal = 0;
        do {
            rndVal = new Random().nextLong();
        } while (rndVal <= 0);
        return rndVal;
    }

    public void setTweetMessage(String tweetMessage)
    {
        this.tweetMessage = tweetMessage;
    }

    public String getTweetMessage()
    {
        return tweetMessage;
    }

    public String getDateString() {
        return dateString();
    }

    public Long getUserId() {
        return userId;
    }

    private String dateString() {
        String dateFormat = "EEE d MMM yyyy H:mm";
        return android.text.format.DateFormat.format(dateFormat, date).toString();
    }

    public String getEmailableTweet() {
        String tweetString = "Tweet: " + tweetMessage + " Date: " + dateString();
        return tweetString;
    }
}

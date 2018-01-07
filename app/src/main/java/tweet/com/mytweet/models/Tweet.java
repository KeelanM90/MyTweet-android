package tweet.com.mytweet.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by keela on 01/11/2017.
 *
 * reference: All aspects of this app are heavily based around the below tutorials with parts of the original code remaining
 * https://wit-ictskills-2017.github.io/mobile-app-dev/labwall.html
 */

public class Tweet {
    public String _id;
    public String date;
    public String img;
    public File image;
    private String tweet;
    public String tweeterName;
    public User tweeter;

    private static final String JSON_ID = "_id";
    private static final String JSON_TWEET_MESSAGE = "tweetmessage";
    private static final String JSON_DATE = "date";
    private static final String JSON_IMG = "img";
    private static final String JSON_TWEETER_NAME = "userid";

    public Tweet() {
        this.date = "";
        this.tweet = "";
        this.image = null;
    }

    public Tweet(JSONObject json) throws JSONException {
        _id = json.getString(JSON_ID);
        tweet = json.getString(JSON_TWEET_MESSAGE);
        img = json.getString(JSON_IMG);
        date = json.getString(JSON_DATE);
        tweeterName = json.getString(JSON_TWEETER_NAME);
    }

    public JSONObject toJSON() throws JSONException {
        if (img == null) {
            img = "";
        }
        JSONObject json = new JSONObject();
        json.put(JSON_ID, _id);
        json.put(JSON_TWEET_MESSAGE, tweet);
        json.put(JSON_IMG, img);
        json.put(JSON_DATE, date);
        json.put(JSON_TWEETER_NAME, this.tweeterName);
        Log.e("tweets", tweet);
        return json;
    }

    /**
     * Src: Android labs - https://wit-ictskills-2017.github.io/mobile-app-dev/topic03-a/book-c-myrent-01%20(Widgets)/index.html#/05
     * Generate a long greater than zero
     *
     * @return Unsigned Long value greater than zero
     */
    private Long unsignedLong() {
        long rndVal = 0;
        do {
            rndVal = new Random().nextLong();
        } while (rndVal <= 0);
        return rndVal;
    }

    public void setTweetMessage(String tweetMessage) {
        this.tweet = tweetMessage;
    }

    public String getTweetMessage() {
        return tweet;
    }

    public String getDateString() {
        return dateString();
    }

    public String getUserId() {
        return this.tweeter._id;
    }

    private String dateString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat readableFormat = new SimpleDateFormat("MMM d, yyyy hh:mm a");
        String readableDate = "";
        try {
            Date date = format.parse(this.date);
            readableDate = readableFormat.format(date);
        } catch (ParseException e) {
        }
        return readableDate;
    }

    public String getEmailableTweet() {
        String tweetString = "Tweet: " + tweet + " Date: " + dateString();
        return tweetString;
    }
}

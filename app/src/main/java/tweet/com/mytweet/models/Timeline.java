package tweet.com.mytweet.models;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by keela on 01/11/2017.
 */

public class Timeline {
    public ArrayList<Tweet> tweets;

    public Timeline() {
        tweets = new ArrayList<Tweet>();
        this.generateTestData();
    }

    public void addTweet(Tweet tweet) {
        tweets.add(tweet);
    }

    public Tweet getTweet(Long id) {
        Log.i(this.getClass().getSimpleName(), "Long parameter id: " + id);

        for (Tweet tweet : tweets) {
            if (id.equals(tweet.id)) {
                return tweet;
            }
        }
        return null;
    }

    private void generateTestData() {
        for (int i = 0; i < 100; i += 1) {
            Tweet tweet = new Tweet();
            tweet.setTweetMessage("Tweet" + i);;
            tweets.add(tweet);
        }
    }
}

package tweet.com.mytweet.models;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by keela on 01/11/2017.
 */

public class Timeline {
    private TweetSerializer tweetSerializer;
    public ArrayList<Tweet> tweets;

    public Timeline(TweetSerializer tweetSerializer) {

        this.tweetSerializer = tweetSerializer;
        try
        {
            tweets = tweetSerializer.loadTweets();
        }
        catch (Exception e) {
            tweets = new ArrayList<>();
        }
    }

    public void addTweet(Tweet tweet) {
        tweets.add(tweet);
        this.saveTweets();
    }

    public void deleteTweet(Tweet tweet)
    {
        tweets.remove(tweet);
        saveTweets();
    }

    public Tweet getTweet(Long id) {
        for (Tweet tweet : tweets) {
            if (id.equals(tweet.id)) {
                return tweet;
            }
        }
        return null;
    }

    public boolean saveTweets()
    {
        try
        {
            tweetSerializer.saveTweets(tweets);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}

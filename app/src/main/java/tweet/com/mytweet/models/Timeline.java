package tweet.com.mytweet.models;

import java.util.ArrayList;

/**
 * Created by keela on 01/11/2017.
 *
 * reference: All aspects of this app are heavily based around the below tutorials with parts of the original code remaining
 * https://wit-ictskills-2017.github.io/mobile-app-dev/labwall.html
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
        this.saveTweets();
    }

    public Tweet getTweet(Long id) {
        for (Tweet tweet : tweets) {
            if (id.equals(tweet._id)) {
                return tweet;
            }
        }
        return null;
    }

    public  void setTweets(ArrayList<Tweet> newTweets) {
        this.tweets = new ArrayList<>(newTweets);
        saveTweets();
    }

    public void refresh() {
        try
        {
            tweets = tweetSerializer.loadTweets();
        }
        catch (Exception e) {
            tweets = new ArrayList<>();
        }
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

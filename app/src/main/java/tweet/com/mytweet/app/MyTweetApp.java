package tweet.com.mytweet.app;

import android.app.Application;
import tweet.com.mytweet.models.Timeline;
import tweet.com.mytweet.models.Tweet;
import tweet.com.mytweet.models.TweetSerializer;

/**
 * Created by keela on 01/11/2017.
 */

public class MyTweetApp extends Application
{
    public Timeline timeline;
    private static final String TWEETS_FILENAME = "tweets.json";

    @Override
    public void onCreate()
    {
        super.onCreate();
        TweetSerializer tweetSerializer = new TweetSerializer(this, TWEETS_FILENAME);
        timeline = new Timeline(tweetSerializer);
    }
}

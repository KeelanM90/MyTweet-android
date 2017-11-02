package tweet.com.mytweet.app;

import android.app.Application;

import java.util.ArrayList;

import tweet.com.mytweet.models.Timeline;
import tweet.com.mytweet.models.Tweet;
import tweet.com.mytweet.models.TweetSerializer;
import tweet.com.mytweet.models.User;
import tweet.com.mytweet.models.UserSerializer;
import tweet.com.mytweet.models.UserStore;

/**
 * Created by keela on 01/11/2017.
 */

public class MyTweetApp extends Application {
    public Timeline timeline;
    public UserStore userStore;
    private static final String TWEETS_FILENAME = "tweets.json";
    private static final String USERS_FILENAME = "users.json";
    public User loggedInUser;

    protected static MyTweetApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        TweetSerializer tweetSerializer = new TweetSerializer(this, TWEETS_FILENAME);
        timeline = new Timeline(tweetSerializer);
        UserSerializer userSerializer = new UserSerializer(this, USERS_FILENAME);
        userStore = new UserStore(userSerializer);
    }

    public static MyTweetApp getApp() {
        return app;
    }

    public boolean validUser(String email, String password) {
        User user = userStore.getUserByEmail(email);
        if (user != null) {
            if (user.email.equals(email) && user.password.equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void setLoggedInUser(String email) {
        loggedInUser = userStore.getUserByEmail(email);
    }
}

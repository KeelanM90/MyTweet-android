package tweet.com.mytweet.app;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tweet.com.mytweet.helpers.TweetService;
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

    public TweetService tweetService;
    public boolean tweetServiceAvailable = false;
    public String  service_url  = "http://54.246.244.111:4000";   // API IP Address

    public User  currentUser;
    public List<User> users = new ArrayList<User>();

    protected static MyTweetApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
       // TweetSerializer tweetSerializer = new TweetSerializer(this, TWEETS_FILENAME);
       // timeline = new Timeline(tweetSerializer);
        // UserSerializer userSerializer = new UserSerializer(this, USERS_FILENAME);
       // userStore = new UserStore(userSerializer);

        super.onCreate();
        super.onCreate();
        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(service_url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        tweetService = retrofit.create(TweetService.class);

        Log.v("Tweet", "Tweet App Started");
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

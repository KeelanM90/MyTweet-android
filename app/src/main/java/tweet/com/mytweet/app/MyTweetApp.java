package tweet.com.mytweet.app;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tweet.com.mytweet.helpers.RetrofitServiceFactory;
import tweet.com.mytweet.helpers.TweetService;
import tweet.com.mytweet.helpers.TweetServiceOpen;
import tweet.com.mytweet.models.Timeline;
import tweet.com.mytweet.models.Token;
import tweet.com.mytweet.models.Tweet;
import tweet.com.mytweet.models.TweetSerializer;
import tweet.com.mytweet.models.User;
import tweet.com.mytweet.models.UserSerializer;
import tweet.com.mytweet.models.UserStore;

/**
 * Created by keela on 01/11/2017.
 */

public class MyTweetApp extends Application implements Callback<Token> {
    public Timeline timeline;
    public UserStore userStore;
    private static final String TWEETS_FILENAME = "tweets.json";
    private static final String USERS_FILENAME = "users.json";
    public User loggedInUser;

    public TweetService tweetService;
    public TweetServiceOpen tweetServiceOpen;
    public boolean tweetServiceAvailable = false;

    public User  currentUser;
    public List<User> users = new ArrayList<User>();

    protected static MyTweetApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        TweetSerializer tweetSerializer = new TweetSerializer(this, TWEETS_FILENAME);
        timeline = new Timeline(tweetSerializer);

        tweetServiceOpen = RetrofitServiceFactory.createService(TweetServiceOpen.class);

        Log.v("Tweet", "Tweet App Started");
    }

    public static MyTweetApp getApp() {
        return app;
    }

    public boolean validUser (String email, String password)
    {
        User user = new User ("", "", email, password);
        tweetServiceOpen.authenticate(user);
        Call<Token> call = (Call<Token>) tweetServiceOpen.authenticate (user);
        call.enqueue(this);
        return true;
    }

    @Override
    public void onResponse(Call<Token> call, Response<Token> response) {
        Token auth = response.body();
        currentUser = auth.user;
        tweetService =  RetrofitServiceFactory.createService(TweetService.class, auth.token);
    }

    @Override
    public void onFailure(Call<Token> call, Throwable t) {
        Toast toast = Toast.makeText(this, "Unable to authenticate with Tweet Service", Toast.LENGTH_SHORT);
        toast.show();
        Log.v("Tweet", "Failed to Authenticated!");
    }

    public void setLoggedInUser(String email) {
        loggedInUser = userStore.getUserByEmail(email);
    }


    public void serviceUnavailableMessage() {
        Toast toast = Toast.makeText(this, "Tweet Service Unavailable. Try again later", Toast.LENGTH_LONG);
        toast.show();
    }

    public void serviceAvailableMessage() {
        Toast toast = Toast.makeText(this, "Tweet Contacted Successfully", Toast.LENGTH_LONG);
        toast.show();
    }
}

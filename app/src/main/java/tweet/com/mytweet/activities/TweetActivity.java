package tweet.com.mytweet.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import tweet.com.mytweet.R;
import tweet.com.mytweet.app.MyTweetApp;
import tweet.com.mytweet.fragments.TweetFragment;
import tweet.com.mytweet.fragments.ViewTweetFragment;
import tweet.com.mytweet.models.Tweet;

import static tweet.com.mytweet.fragments.TweetFragment.EXTRA_TWEET_ID;

public class TweetActivity extends AppCompatActivity {
    public ActionBar actionBar;
    MyTweetApp app = MyTweetApp.getApp();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);

        actionBar = getSupportActionBar();

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        Long tweetId = (Long) getIntent().getSerializableExtra(EXTRA_TWEET_ID);
        Tweet tweet = app.timeline.getTweet(tweetId);

        if (fragment == null) {
            if (tweet.getUserId().equals(app.loggedInUser.id)) {
                fragment = new TweetFragment();
                manager.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
            } else {
                fragment = new ViewTweetFragment();
                manager.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
            }
        }
    }
}

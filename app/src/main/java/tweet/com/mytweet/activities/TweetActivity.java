package tweet.com.mytweet.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tweet.com.mytweet.R;
import tweet.com.mytweet.app.MyTweetApp;
import tweet.com.mytweet.fragments.TweetFragment;
import tweet.com.mytweet.fragments.ViewTweetFragment;
import tweet.com.mytweet.models.Tweet;

public class TweetActivity extends AppCompatActivity {
    public ActionBar actionBar;
    MyTweetApp app = MyTweetApp.getApp();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);

        actionBar = getSupportActionBar();

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            if (true) {
                fragment = new TweetFragment();
            } else {
                fragment = new ViewTweetFragment();
            }
            manager.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
        }
    }
}

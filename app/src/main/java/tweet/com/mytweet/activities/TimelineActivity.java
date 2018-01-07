package tweet.com.mytweet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import tweet.com.mytweet.R;
import tweet.com.mytweet.fragments.FollowedTimelineFragment;
import tweet.com.mytweet.fragments.GlobalTimelineFragment;
import tweet.com.mytweet.fragments.ProfileFragment;

/**
 * Created by keela on 01/11/2017.
 */

public class TimelineActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = new GlobalTimelineFragment();
            manager.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.abmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_tweet:
                startActivity(new Intent(this, AddTweet.class));
                return true;
            case R.id.action_timeline:
                this.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new GlobalTimelineFragment(), "FragD").commit();
                return true;
            case R.id.action_followed:
                this.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FollowedTimelineFragment(), "FragD").commit();
                return true;
            case R.id.action_profile:
                this.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ProfileFragment(), "FragD").commit();
                return true;
            case R.id.action_settings:
                startActivity(new Intent(this, Settings.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

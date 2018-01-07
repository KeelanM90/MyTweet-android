package tweet.com.mytweet.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tweet.com.mytweet.R;
import tweet.com.mytweet.activities.Settings;
import tweet.com.mytweet.activities.TweetActivity;
import tweet.com.mytweet.app.MyTweetApp;
import tweet.com.mytweet.helpers.IntentHelper;
import tweet.com.mytweet.models.Timeline;
import tweet.com.mytweet.models.Tweet;
import tweet.com.mytweet.models.User;
import tweet.com.mytweet.helpers.TimelineAdapter;


/**
 * Created by keela on 02/11/2017.
 *
 * reference: All aspects of this app are heavily based around the below tutorials
 * https://wit-ictskills-2017.github.io/mobile-app-dev/labwall.html
 */

public class TimelineFragment extends ListFragment implements OnItemClickListener, AbsListView.MultiChoiceModeListener, Callback<List<Tweet>> {
    private ArrayList<Tweet> tweets = new ArrayList<>();
    private Timeline timeline;
    private TimelineAdapter adapter;
    private MyTweetApp app;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.app_name);

        app = MyTweetApp.getApp();
        timeline = app.timeline;
        tweets = timeline.tweets;

       adapter = new TimelineAdapter(getActivity(), tweets);
       setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);
        listView = (ListView) v.findViewById(android.R.id.list);
        listView.setDivider(null);
        listView.setDividerHeight(0);
        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Tweet tweet = ((TimelineAdapter) getListAdapter()).getItem(position);
        Intent i = new Intent(getActivity(), TweetActivity.class);
        //i.putExtra(TweetFragment.EXTRA_TWEET_ID, tweet._id);
        startActivityForResult(i, 0);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Tweet tweet = adapter.getItem(position);
        IntentHelper.startActivityWithData(getActivity(), TweetActivity.class, "TWEET_ID", tweet._id);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.abmenu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /**
             case R.id.menu_item_new_tweet:
                Tweet tweet = new Tweet(app.loggedInUser.id);
                timeline.addTweet(tweet);
                Intent i = new Intent(getActivity(), TweetActivity.class);
                i.putExtra(TweetFragment.EXTRA_TWEET_ID, tweet.id);
                startActivityForResult(i, 0);
                return true;
            case R.id.action_clear:
                for (int j = tweets.size() - 1; j >= 0; j--) {
                    timeline.deleteTweet(tweets.get(j));
                    adapter.notifyDataSetChanged();
                }
                return true;
         */
            case R.id.action_settings:
                startActivity(new Intent(getActivity(), Settings.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getTweets();
    }

    /* ************ MultiChoiceModeListener methods (begin) *********** */
    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.timeline_context, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_item_delete_tweet:
                deleteTweet(actionMode);
                return true;
            default:
                return false;
        }
    }

    private void deleteTweet(ActionMode actionMode) {
        for (int i = adapter.getCount() - 1; i >= 0; i--) {
            if (listView.isItemChecked(i)) {
                timeline.deleteTweet(adapter.getItem(i));
            }
        }
        actionMode.finish();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int position, long id, boolean checked) {
    }

    public void getTweets(){
        Call<List<Tweet>> call = (Call<List<Tweet>>) app.tweetService.getFollowedTweets();
        call.enqueue(this);
    }

    public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {

        for(Tweet tweet: response.body()){
            tweet.tweeter = (User) tweet.tweeter;
            tweet.tweeterName = tweet.tweeter.firstName + " " + tweet.tweeter.lastName;
            tweets.add(tweet);
        }

        adapter.notifyDataSetChanged();
        app.tweetServiceAvailable = true;
    }

    @Override
    public void onFailure(Call<List<Tweet>> call, Throwable t) {
        Toast.makeText(getActivity(), "Connection error, unable to retrieve tweets", Toast.LENGTH_SHORT).show();
        app.tweetServiceAvailable = false;
    }
}

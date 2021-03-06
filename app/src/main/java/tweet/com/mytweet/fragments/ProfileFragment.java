package tweet.com.mytweet.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tweet.com.mytweet.R;
import tweet.com.mytweet.app.MyTweetApp;
import tweet.com.mytweet.models.Tweet;
import tweet.com.mytweet.models.User;
import tweet.com.mytweet.helpers.TimelineAdapter;


/**
 * Created by keela on 02/11/2017.
 *
 * reference: All aspects of this app are heavily based around the below tutorials
 * https://wit-ictskills-2017.github.io/mobile-app-dev/labwall.html
 */

public class ProfileFragment extends ListFragment implements AbsListView.MultiChoiceModeListener, Callback<List<Tweet>> {
    private ArrayList<Tweet> tweets = new ArrayList<>();
    private TimelineAdapter adapter;
    private MyTweetApp app;

    protected SwipeRefreshLayout swipeRefreshLayout;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.app_name);

        app = MyTweetApp.getApp();

       adapter = new TimelineAdapter(getActivity(), tweets);
       setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.swipe_refresh,
                parent, false);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTweets();
            }
        });
        listView = (ListView) rootView.findViewById(android.R.id.list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(this);
        return rootView;
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
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_clear);
        item.setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear:
                for (Tweet tweet : tweets) {
                    deleteTweetHelper(tweet._id);
                }
                return true;
                default:
                    return true;
        }
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
                deleteTweetHelper(adapter.getItem(i)._id);
            }
        }
        actionMode.finish();
    }

    public void deleteTweetHelper(String _id) {
        Call<String> call = (Call<String>) app.tweetService.deleteTweet(_id);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                getTweets();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getActivity(), "Could not delete tweet!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int position, long id, boolean checked) {
    }

    public void getTweets(){
        Call<List<Tweet>> call = (Call<List<Tweet>>) app.tweetService.getUsersTweets(app.currentUser._id);
        call.enqueue(this);
    }

    public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
        tweets.clear();
        for (Tweet tweet : response.body()) {
            tweet.tweeter = (User) tweet.tweeter;
            tweet.tweeterName = tweet.tweeter.firstName + " " + tweet.tweeter.lastName;
            tweets.add(tweet);
        }

        adapter.notifyDataSetChanged();
        app.tweetServiceAvailable = true;

        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onFailure(Call<List<Tweet>> call, Throwable t) {
        Toast.makeText(getActivity(), "Connection error, unable to retrieve tweets, returning to global timeline", Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new GlobalTimelineFragment(), "FragD").commit();
        app.tweetServiceAvailable = false;

        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        adapter.notifyDataSetInvalidated();
    }
}

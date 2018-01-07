package tweet.com.mytweet.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tweet.com.mytweet.R;
import tweet.com.mytweet.app.MyTweetApp;
import tweet.com.mytweet.helpers.TimelineAdapter;
import tweet.com.mytweet.models.Tweet;
import tweet.com.mytweet.models.User;


/**
 * Created by keela on 02/11/2017.
 *
 * reference: All aspects of this app are heavily based around the below tutorials
 * https://wit-ictskills-2017.github.io/mobile-app-dev/labwall.html
 */

public class FollowedTimelineFragment extends ListFragment implements Callback<List<Tweet>> {
    private ArrayList<Tweet> tweets = new ArrayList<>();
    private TimelineAdapter adapter;
    private MyTweetApp app;

    protected SwipeRefreshLayout swipeRefreshLayout;

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
        View rootView = inflater.inflate(R.layout.swipe_refresh,
                parent, false);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTweets();
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getTweets();
    }

    public void getTweets(){
        Call<List<Tweet>> call = (Call<List<Tweet>>) app.tweetService.getFollowedTweets();
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

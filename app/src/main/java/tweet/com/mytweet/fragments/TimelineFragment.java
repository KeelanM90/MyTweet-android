package tweet.com.mytweet.fragments;

import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import tweet.com.mytweet.R;
import tweet.com.mytweet.activities.TweetActivity;
import tweet.com.mytweet.app.MyTweetApp;
import tweet.com.mytweet.helpers.IntentHelper;
import tweet.com.mytweet.models.Timeline;
import tweet.com.mytweet.models.Tweet;


/**
 * Created by keela on 02/11/2017.
 */

public class TimelineFragment extends ListFragment implements OnItemClickListener,  AbsListView.MultiChoiceModeListener {
    private ArrayList<Tweet> tweets;
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
        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Tweet tweet = ((TimelineAdapter) getListAdapter()).getItem(position);
        Intent i = new Intent(getActivity(), TweetActivity.class);
        i.putExtra(TweetFragment.EXTRA_TWEET_ID, tweet.id);
        startActivityForResult(i, 0);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Tweet tweet = adapter.getItem(position);
        IntentHelper.startActivityWithData(getActivity(), TweetActivity.class, "TWEET_ID", tweet.id);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.abmenu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_tweet:
                Tweet tweet = new Tweet(app.loggedInUser.id);
                timeline.addTweet(tweet);
                Intent i = new Intent(getActivity(), TweetActivity.class);
                i.putExtra(TweetFragment.EXTRA_TWEET_ID, tweet.id);
                startActivityForResult(i, 0);
                return true;
            case R.id.action_clear:

                for (int j  = tweets.size()  - 1; j >= 0; j--) {
                    timeline.deleteTweet(tweets.get(j));
                    adapter.notifyDataSetChanged();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        for (Tweet tweet: tweets) {
            if (tweet.getTweetMessage().length() == 0)
                timeline.deleteTweet(tweet);
        }
        adapter.notifyDataSetChanged();
    }

    /* ************ MultiChoiceModeListener methods (begin) *********** */
    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu)
    {
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.timeline_context, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu)
    {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.menu_item_delete_tweet:
                deleteTweet(actionMode);
                return true;
            default:
                return false;
        }
    }

    private void deleteTweet(ActionMode actionMode)
    {
        for (int i = adapter.getCount() - 1; i >= 0; i--)
        {
            if (listView.isItemChecked(i))
            {
                timeline.deleteTweet(adapter.getItem(i));
            }
        }
        actionMode.finish();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode)
    {
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int position, long id, boolean checked)
    {
    }
}

class TimelineAdapter extends ArrayAdapter<Tweet> {
    private Context context;

    public TimelineAdapter(Context context, ArrayList<Tweet> tweets) {
        super(context, 0, tweets);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.tweet_view, null);
        }
        Tweet tweet = getItem(position);

        TextView tweetBody = (TextView) convertView.findViewById(R.id.tweetBody);
        tweetBody.setText(tweet.getTweetMessage());

        TextView dateTextView = (TextView) convertView.findViewById(R.id.tweetDate);
        dateTextView.setText(tweet.getDateString());

        return convertView;
    }
}

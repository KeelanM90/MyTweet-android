package tweet.com.mytweet.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import tweet.com.mytweet.R;
import tweet.com.mytweet.app.MyTweetApp;
import tweet.com.mytweet.models.Timeline;
import tweet.com.mytweet.models.Tweet;

import static tweet.com.mytweet.helpers.IntentHelper.startActivityWithData;
import static tweet.com.mytweet.helpers.IntentHelper.startActivityWithDataForResult;

/**
 * Created by keela on 01/11/2017.
 */

public class TimelineActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    private ListView listView;
    private Timeline timeline;
    private TimelineAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.timeline);

        listView = (ListView) findViewById(R.id.timelineList);
        MyTweetApp app = (MyTweetApp) getApplication();
        timeline = app.timeline;

        adapter = new TimelineAdapter(this, timeline.tweets);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Tweet tweet = adapter.getItem(position);
        startActivityWithData(this, TweetActivity.class, "TWEET_ID", tweet.id);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.abmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_item_new_tweet: Tweet tweet = new Tweet();
                timeline.addTweet(tweet);
                startActivityWithDataForResult(this, TweetActivity.class, "TWEET_ID", tweet.id, 0);
                adapter.notifyDataSetChanged();
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }
}


class TimelineAdapter extends ArrayAdapter<Tweet>
{
    private Context context;

    public TimelineAdapter(Context context, ArrayList<Tweet> tweets)
    {
        super(context, 0, tweets);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
        {
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
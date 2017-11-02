package tweet.com.mytweet.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import tweet.com.mytweet.R;
import tweet.com.mytweet.app.MyTweetApp;
import tweet.com.mytweet.models.Timeline;
import tweet.com.mytweet.models.Tweet;

import static tweet.com.mytweet.helpers.IntentHelper.navigateUp;

public class TweetActivity extends AppCompatActivity implements TextWatcher {

    private EditText tweetBody;
    private TextView textCounter;
    private Tweet tweet;
    private Timeline timeline;
    private TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MyTweetApp app = (MyTweetApp) getApplication();
        timeline = app.timeline;

        Long tweetId = (Long) getIntent().getExtras().getSerializable("TWEET_ID");
        tweet = timeline.getTweet(tweetId);

        tweetBody = (EditText) findViewById(R.id.tweetBody);
        textCounter = (TextView) findViewById(R.id.charCount);
        tweetBody.addTextChangedListener(this);
        date = (TextView) findViewById(R.id.dateText);

        date.setText(tweet.getDateString());

        if (tweet != null)
        {
            updateControls(tweet);
        }
    }

    public void updateControls(Tweet tweet)
    {
        tweetBody.setText(tweet.getTweetMessage());
        date.setText(tweet.getDateString());
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        int charsLeft = 140 - tweetBody.getText().toString().length();
        textCounter.setText(Integer.toString(charsLeft));
    }

    @Override
    public void afterTextChanged(Editable editable) {
        tweet.setTweetMessage(editable.toString());
    }

    @Override
    public void onPause()
    {
        super.onPause();
        timeline.saveTweets();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:  navigateUp(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

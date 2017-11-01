package tweet.com.mytweet.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import tweet.com.mytweet.R;
import tweet.com.mytweet.app.MyTweetApp;
import tweet.com.mytweet.models.Timeline;
import tweet.com.mytweet.models.Tweet;

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

        tweet = new Tweet();

        tweetBody = (EditText) findViewById(R.id.tweetBody);
        textCounter = (TextView) findViewById(R.id.charCount);
        tweetBody.addTextChangedListener(this);
        date = (TextView) findViewById(R.id.dateText);

        date.setText(tweet.getDateString());

        MyTweetApp app = (MyTweetApp) getApplication();
        timeline = app.timeline;

        Long tweetId = (Long) getIntent().getExtras().getSerializable("TWEET_ID");
        tweet = timeline.getTweet(tweetId);

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
}

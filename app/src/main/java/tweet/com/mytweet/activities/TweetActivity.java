package tweet.com.mytweet.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import tweet.com.mytweet.R;
import tweet.com.mytweet.models.Tweet;

public class TweetActivity extends AppCompatActivity implements TextWatcher {

    private EditText tweetBody;
    private TextView textCounter;
    private Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);

        tweet = new Tweet();

        tweetBody = (EditText) findViewById(R.id.tweetBody);
        textCounter = (TextView) findViewById(R.id.charCount);
        tweetBody.addTextChangedListener(this);
        TextView date = (TextView) findViewById(R.id.dateText);

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

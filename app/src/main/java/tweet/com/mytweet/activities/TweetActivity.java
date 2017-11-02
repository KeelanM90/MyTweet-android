package tweet.com.mytweet.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import tweet.com.mytweet.R;
import tweet.com.mytweet.app.MyTweetApp;
import tweet.com.mytweet.models.Timeline;
import tweet.com.mytweet.models.Tweet;
import tweet.com.mytweet.models.User;

import static tweet.com.mytweet.helpers.ContactHelper.getContact;
import static tweet.com.mytweet.helpers.ContactHelper.getEmail;
import static tweet.com.mytweet.helpers.ContactHelper.sendEmail;
import static tweet.com.mytweet.helpers.IntentHelper.navigateUp;
import static tweet.com.mytweet.helpers.IntentHelper.selectContact;

public class TweetActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener {

    MyTweetApp app;

    private EditText tweetBody;
    private TextView textCounter;
    private Tweet tweet;
    private Timeline timeline;
    private TextView date;
    private Button emailButton;

    Button contactButton;

    private String emailAddress = "";
    private Intent data;

    private static final int REQUEST_CONTACT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        app = (MyTweetApp) getApplication();
        timeline = app.timeline;

        Long tweetId = (Long) getIntent().getExtras().getSerializable("TWEET_ID");
        tweet = timeline.getTweet(tweetId);

        tweetBody = (EditText) findViewById(R.id.tweetBody);
        textCounter = (TextView) findViewById(R.id.charCount);
        date = (TextView) findViewById(R.id.dateText);
        contactButton = (Button) findViewById(R.id.contactButton);
        emailButton = (Button) findViewById(R.id.emailButton);

        emailButton.setOnClickListener(this);
        contactButton.setOnClickListener(this);
        tweetBody.addTextChangedListener(this);

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.contactButton:
                selectContact(this, REQUEST_CONTACT);
                break;

            case R.id.emailButton :
                User user = app.userStore.getUser(tweet.getUserId());
                sendEmail(this, emailAddress, "Tweet from " + user.firstName + " " + user.lastName,tweet.getEmailableTweet());
                break;
        }
    }

    public void readContact() {
        String name = getContact(this, data);
        emailAddress = getEmail(this, data);
        contactButton.setText(name + " : " + emailAddress);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONTACT:
                this.data = data;
                checkContactsReadPermission();
                break;
        }
    }

    //https://developer.android.com/training/permissions/requesting.html
    private void checkContactsReadPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            //We can request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CONTACT);
        }
        else {
            //We already have permission, so go head and read the contact
            readContact();
        }
    }

    //https://developer.android.com/training/permissions/requesting.html
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CONTACT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    readContact();
                }
            }
        }
    }
}

package tweet.com.mytweet.fragments;

/**
 * Created by keela on 02/11/2017.
 */

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import tweet.com.mytweet.R;
import tweet.com.mytweet.activities.TweetActivity;
import tweet.com.mytweet.app.MyTweetApp;
import tweet.com.mytweet.models.Timeline;
import tweet.com.mytweet.models.Tweet;
import tweet.com.mytweet.models.User;

import static tweet.com.mytweet.helpers.ContactHelper.getContact;
import static tweet.com.mytweet.helpers.ContactHelper.getEmail;
import static tweet.com.mytweet.helpers.ContactHelper.sendEmail;

public class TweetFragment extends Fragment implements TextWatcher, View.OnClickListener {

    MyTweetApp app;

    private EditText tweetBody;
    private TextView textCounter;
    private Tweet tweet;
    private Timeline timeline;
    private TextView date;
    private Button emailButton;
    private Button tweetButton;

    Button contactButton;

    private String emailAddress = "";
    private Intent data;

    private static final int REQUEST_CONTACT = 1;
    public static final String EXTRA_TWEET_ID = "mytweet.TWEET_ID";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        app = MyTweetApp.getApp();

        Long tweetId = (Long) getActivity().getIntent().getSerializableExtra(EXTRA_TWEET_ID);

        timeline = app.timeline;
        tweet = timeline.getTweet(tweetId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_tweet, parent, false);

        TweetActivity tweetActivity = (TweetActivity) getActivity();
        tweetActivity.actionBar.setDisplayHomeAsUpEnabled(true);

        addListeners(v);
        updateControls(tweet);

        return v;
    }

    private void addListeners(View v) {
        tweetBody = (EditText) v.findViewById(R.id.tweetBody);
        textCounter = (TextView) v.findViewById(R.id.charCount);
        date = (TextView) v.findViewById(R.id.dateText);
        contactButton = (Button) v.findViewById(R.id.contactButton);
        emailButton = (Button) v.findViewById(R.id.emailButton);
        tweetButton = (Button) v.findViewById(R.id.tweetButton);

        emailButton.setOnClickListener(this);
        contactButton.setOnClickListener(this);
        tweetButton.setOnClickListener(this);
        tweetBody.addTextChangedListener(this);

        date.setText(tweet.getDateString());

        updateControls(tweet);
    }

    public void updateControls(Tweet tweet) {
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
    public void onPause() {
        super.onPause();
        timeline.saveTweets();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.contactButton:
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(i, REQUEST_CONTACT);
                break;
            case R.id.emailButton:
                User user = app.userStore.getUser(tweet.getUserId());
                sendEmail(getActivity(), emailAddress, "Tweet from " + user.firstName + " " + user.lastName, tweet.getEmailableTweet());
                break;
            case R.id.tweetButton:
                getActivity().finish();
                break;
        }
    }

    public void readContact() {
        String name = getContact(getActivity(), data);
        emailAddress = getEmail(getActivity(), data);
        contactButton.setText(name + " : " + emailAddress);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONTACT:
                this.data = data;
                if (data != null)
                    checkContactsReadPermission();
                break;
        }
    }

    //https://developer.android.com/training/permissions/requesting.html
    private void checkContactsReadPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            //We can request the permission.
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CONTACT);
        } else {
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

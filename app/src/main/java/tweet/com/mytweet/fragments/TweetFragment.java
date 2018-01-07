/*
package tweet.com.mytweet.fragments;

*/
/**
 * Created by keela on 02/11/2017.
 * <p>
 * reference: All aspects of this app are heavily based around the below tutorials with parts of the original code remaining
 * https://wit-ictskills-2017.github.io/mobile-app-dev/labwall.html
 *//*


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
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

import java.io.ByteArrayOutputStream;
import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tweet.com.mytweet.R;
import tweet.com.mytweet.activities.TweetActivity;
import tweet.com.mytweet.app.MyTweetApp;
import tweet.com.mytweet.models.Timeline;
import tweet.com.mytweet.models.Tweet;

public class TweetFragment extends Fragment implements TextWatcher, View.OnClickListener, Callback<Tweet> {

    MyTweetApp app;

    private EditText tweetBody;
    private TextView textCounter;
    private Timeline timeline;
    private Button tweetButton;
    private Button tweetCameraButton;
    byte[] img = null;

    private static final int REQUEST_CODE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        app = MyTweetApp.getApp();

        timeline = app.timeline;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_tweet, parent, false);

        TweetActivity tweetActivity = (TweetActivity) getActivity();
        tweetActivity.actionBar.setDisplayHomeAsUpEnabled(true);

        addListeners(v);

        return v;
    }

    private void addListeners(View v) {
        tweetBody = (EditText) v.findViewById(R.id.tweetBody);
        textCounter = (TextView) v.findViewById(R.id.charCount);
        tweetButton = (Button) v.findViewById(R.id.tweetButton);
        tweetCameraButton = (Button) v.findViewById(R.id.tweetCameraButton);

        tweetCameraButton.setOnClickListener(this);
        tweetButton.setOnClickListener(this);
        tweetBody.addTextChangedListener(this);
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
    public void afterTextChanged(Editable s) {

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
            case R.id.tweetCameraButton:
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                getActivity().startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.tweetButton:
                String tweetMessage = tweetBody.getText().toString();
                Tweet tweet = new Tweet();
                if (tweetMessage.length() > 0) {
                    tweet.setTweetMessage(tweetMessage);
                    tweet.image = img;
                    Call<Tweet> call = (Call<Tweet>) app.tweetService.addTweet(tweet);
                    call.enqueue(this);
                } else {
                    Toast.makeText(app, "Please enter tweet message!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onResponse(Call<Tweet> call, Response<Tweet> response) {
        app.tweetServiceAvailable = false;
        getActivity().finish();
    }

    @Override
    public void onFailure(Call<Tweet> call, Throwable t) {
        app.serviceUnavailableMessage();
        app.tweetServiceAvailable = false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            Toast.makeText(app, "Result", Toast.LENGTH_SHORT).show();
            Bitmap bmp = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            img = stream.toByteArray();
            Toast.makeText(app, "Result" + img, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Image Capture Failed", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}

*/

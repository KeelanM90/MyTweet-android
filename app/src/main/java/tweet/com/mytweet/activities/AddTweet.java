package tweet.com.mytweet.activities;

/**
 * Created by keela on 02/11/2017.
 * <p>
 * reference: All aspects of this app are heavily based around the below tutorials with parts of the original code remaining
 * https://wit-ictskills-2017.github.io/mobile-app-dev/labwall.html
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tweet.com.mytweet.R;
import tweet.com.mytweet.app.MyTweetApp;
import tweet.com.mytweet.models.Timeline;
import tweet.com.mytweet.models.Tweet;

public class AddTweet extends Activity implements TextWatcher, View.OnClickListener, Callback<Tweet> {

    MyTweetApp app;

    private EditText tweetBody;
    private TextView textCounter;
    private Timeline timeline;
    private Button tweetButton;
    private Button tweetCameraButton;
    String img = null;

    private static final int REQUEST_CODE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_tweet);

        app = MyTweetApp.getApp();

        timeline = app.timeline;
        addListeners();
    }

    private void addListeners() {
        tweetBody = (EditText) this.findViewById(R.id.tweetBody);
        textCounter = (TextView) this.findViewById(R.id.charCount);
        tweetButton = (Button) this.findViewById(R.id.tweetButton);
        tweetCameraButton = (Button) this.findViewById(R.id.tweetCameraButton);

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
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tweetCameraButton:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                this.startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.tweetButton:
                String tweetMessage = tweetBody.getText().toString();
                Tweet tweet = new Tweet();
                if (tweetMessage.length() > 0) {
                    tweet.setTweetMessage(tweetMessage);
                    tweet.image =  img;
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
        this.finish();
    }

    @Override
    public void onFailure(Call<Tweet> call, Throwable t) {
        app.serviceUnavailableMessage();
        app.tweetServiceAvailable = false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Bitmap bmp = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 40, stream);

            img = "data:image/png;base64," + Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
            tweetCameraButton.setText("Image Added");
        } else {
            Toast.makeText(this, "Image Capture Failed", Toast.LENGTH_SHORT).show();
        }
    }
}


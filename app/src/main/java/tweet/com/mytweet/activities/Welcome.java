package tweet.com.mytweet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tweet.com.mytweet.R;
import tweet.com.mytweet.app.MyTweetApp;
import tweet.com.mytweet.models.User;

/**
 * Created by keela on 01/11/2017.
 */

public class Welcome extends AppCompatActivity implements Callback<Boolean> {
    private MyTweetApp app;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        app = (MyTweetApp) getApplication();
    }

    @Override
    public void onResume() {
        super.onResume();
        app.currentUser = null;
        Call<Boolean> call1 = (Call<Boolean>) app.tweetService.getAvailability();
        call1.enqueue(this);
    }

    @Override
    public void onResponse(Call<Boolean> call, Response<Boolean> response)
    {
        serviceAvailableMessage();
        app.tweetServiceAvailable = true;
    }

    @Override
    public void onFailure(Call<Boolean> call, Throwable t)
    {
        app.tweetServiceAvailable = false;
        serviceUnavailableMessage();
    }

    public void loginPressed(View view) {
        if (app.tweetServiceAvailable) {
            startActivity(new Intent(this, Login.class));
        } else {
            serviceUnavailableMessage();
        }
    }

    public void signupPressed(View view) {
        if (app.tweetServiceAvailable) {
            startActivity(new Intent(this, Signup.class));
        } else {
            serviceUnavailableMessage();
        }
    }

    void serviceUnavailableMessage() {
        Toast toast = Toast.makeText(this, "Tweet Service Unavailable. Try again later", Toast.LENGTH_LONG);
        toast.show();
    }

    void serviceAvailableMessage() {
        Toast toast = Toast.makeText(this, "Tweet Contacted Successfully", Toast.LENGTH_LONG);
        toast.show();
    }
}


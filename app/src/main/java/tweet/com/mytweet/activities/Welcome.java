package tweet.com.mytweet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tweet.com.mytweet.R;
import tweet.com.mytweet.app.MyTweetApp;

/**
 * Created by keela on 01/11/2017.
 */

public class Welcome extends AppCompatActivity {
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
        Call<Boolean> call = (Call<Boolean>) app.tweetServiceOpen.getAvailability();
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                app.tweetServiceAvailable = true;
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                app.serviceUnavailableMessage();
            }
        });
    }

    public void loginPressed(View view) {
        if (app.tweetServiceAvailable) {
            startActivity(new Intent(this, Login.class));
        } else {
            app.serviceUnavailableMessage();
        }
    }

    public void signupPressed(View view) {
        if (app.tweetServiceAvailable) {
            startActivity(new Intent(this, Signup.class));
        } else {
            app.serviceUnavailableMessage();
        }
    }
}


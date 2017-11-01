package tweet.com.mytweet.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import tweet.com.mytweet.R;
import tweet.com.mytweet.models.Timeline;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginPressed (View view)
    {
        startActivity (new Intent(this, TimelineActivity.class));
    }
}
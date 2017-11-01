package tweet.com.mytweet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import tweet.com.mytweet.R;

/**
 * Created by keela on 01/11/2017.
 */

public class Welcome extends AppCompatActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void loginPressed(View view) {
        startActivity(new Intent(this, Login.class));
    }

    public void signupPressed(View view) {
        startActivity(new Intent(this, Signup.class));
    }
}


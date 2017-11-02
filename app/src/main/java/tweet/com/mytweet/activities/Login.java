package tweet.com.mytweet.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import tweet.com.mytweet.R;
import tweet.com.mytweet.app.MyTweetApp;
import tweet.com.mytweet.models.Timeline;
import tweet.com.mytweet.models.TweetSerializer;
import tweet.com.mytweet.models.UserStore;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginPressed (View view)
    {
        MyTweetApp app = MyTweetApp.getApp();;

        EditText email     = (EditText)  findViewById(R.id.email);
        EditText password  = (EditText)  findViewById(R.id.password);

        String emailAddress = email.getText().toString();
        if (app.validUser(emailAddress, password.getText().toString()))
        {
            app.setLoggedInUser(emailAddress);
            startActivity (new Intent(this, TimelineActivity.class));
        }
        else
        {
            Toast toast = Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
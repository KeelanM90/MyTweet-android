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

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginPressed (View view)
    {
        MyTweetApp app = (MyTweetApp) getApplication();

        EditText email     = (EditText)  findViewById(R.id.email);
        EditText password  = (EditText)  findViewById(R.id.password);

        if (app.validUser(email.getText().toString(), password.getText().toString()))
        {
            startActivity (new Intent(this, TimelineActivity.class));
        }
        else
        {
            Toast toast = Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
package tweet.com.mytweet.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import tweet.com.mytweet.R;
import tweet.com.mytweet.app.MyTweetApp;
import tweet.com.mytweet.models.User;

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void signupPressed (View view)
    {
        EditText firstName = (EditText)  findViewById(R.id.firstName);
        EditText lastName  = (EditText)  findViewById(R.id.lastName);
        EditText email     = (EditText)  findViewById(R.id.Email);
        EditText password  = (EditText)  findViewById(R.id.Password);

        User user = new User(firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), password.getText().toString());

        MyTweetApp app = (MyTweetApp) getApplication();
        app.userStore.addUser(user);

        startActivity (new Intent(this, Welcome.class));
    }
}

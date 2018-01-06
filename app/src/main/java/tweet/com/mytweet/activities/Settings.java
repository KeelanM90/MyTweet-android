package tweet.com.mytweet.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tweet.com.mytweet.R;
import tweet.com.mytweet.app.MyTweetApp;
import tweet.com.mytweet.models.User;

public class Settings extends AppCompatActivity implements Callback<User> {

    MyTweetApp app;
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        app = (MyTweetApp) getApplication();

        firstName = (EditText)  findViewById(R.id.firstName);
        lastName  = (EditText)  findViewById(R.id.lastName);
        email = (EditText)  findViewById(R.id.Email);
        password  = (EditText)  findViewById(R.id.Password);

        firstName.setText(app.currentUser.firstName);
        lastName.setText(app.currentUser.lastName);
        email.setText(app.currentUser.email);
    }

    public void updatePressed (View view)
    {
        User user = app.currentUser;
        user.firstName = firstName.getText().toString();
        user.lastName = lastName.getText().toString();
        user.email = email.getText().toString();
        user.password = password.getText().toString();
        Call<User> call = (Call<User>) app.tweetService.updateUser(user);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response)
    {
        Toast.makeText(getApplicationContext(), response.body().firstName + " was updated!", Toast.LENGTH_LONG).show();
        this.finish();
    }

    @Override
    public void onFailure(Call<User> call, Throwable t)
    {
        app.tweetServiceAvailable = false;
        Toast.makeText(this, "Tweet Service Unavailable. Try again later", Toast.LENGTH_LONG).show();
    }
}

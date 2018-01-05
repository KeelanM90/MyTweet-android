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

public class Signup extends AppCompatActivity implements Callback<User> {

    MyTweetApp app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        app = (MyTweetApp) getApplication();
    }

    public void signupPressed (View view)
    {
        EditText firstName = (EditText)  findViewById(R.id.firstName);
        EditText lastName  = (EditText)  findViewById(R.id.lastName);
        EditText email     = (EditText)  findViewById(R.id.Email);
        EditText password  = (EditText)  findViewById(R.id.Password);

        User user = new User(firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), password.getText().toString());

        app = (MyTweetApp) getApplication();
        Call<User> call = (Call<User>) app.tweetService.createUser(user);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response)
    {
        app.users.add(response.body());

        //Toast.makeText(this, response.body().firstName + " " + response.body().lastName + "added", Toast.LENGTH_LONG).show();

        startActivity(new Intent(this, Welcome.class));
    }

    @Override
    public void onFailure(Call<User> call, Throwable t)
    {
        app.tweetServiceAvailable = false;
        Toast toast = Toast.makeText(this, "Tweet Service Unavailable. Try again later", Toast.LENGTH_LONG);
        toast.show();
        startActivity (new Intent(this, Welcome.class));
    }
}

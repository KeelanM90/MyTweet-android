package tweet.com.mytweet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tweet.com.mytweet.R;
import tweet.com.mytweet.app.MyTweetApp;
import tweet.com.mytweet.helpers.RetrofitServiceFactory;
import tweet.com.mytweet.helpers.TweetService;
import tweet.com.mytweet.models.Token;
import tweet.com.mytweet.models.User;

public class Login extends AppCompatActivity implements Callback<Token> {

    MyTweetApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        app = MyTweetApp.getApp();;
    }

    public void loginPressed (View view)
    {
        EditText email     = (EditText)  findViewById(R.id.email);
        EditText passwordText  = (EditText)  findViewById(R.id.password);

        String emailAddress = email.getText().toString();
        String password = passwordText.getText().toString();
        if (emailAddress != "" && password  != "")
        {
            User tempUser = new User(emailAddress, password);
            Call<Token> call = (Call<Token>) app.tweetServiceOpen.authenticate(tempUser);
            call.enqueue(this);
        }
        else
        {
            Toast toast = Toast.makeText(this, "Please enter both an email and password", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onResponse(Call<Token> call, Response<Token> response) {
        app.tweetServiceAvailable = true;
        Token auth = response.body();
        if(auth != null && auth.success != false) {
            app.currentUser = auth.user;
            app.tweetService = RetrofitServiceFactory.createService(TweetService.class, auth.token);
            startActivity(new Intent(this, ContainerActivity.class));
        }
    }

    @Override
    public void onFailure(Call<Token> call, Throwable t) {
        Toast toast = Toast.makeText(app, "Log in Failed", Toast.LENGTH_SHORT);
    }
}
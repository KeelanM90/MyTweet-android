package tweet.com.mytweet.helpers;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import tweet.com.mytweet.models.Token;
import tweet.com.mytweet.models.User;

public interface TweetServiceOpen
{
    @POST("/api/users")
    Call<User> createUser(@Body User newUser);

    @POST("/api/users/authenticate")
    Call<Token> authenticate(@Body User user);

    @GET("/api/availability")
    Call<Boolean> getAvailability();
}
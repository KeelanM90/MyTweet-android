package tweet.com.mytweet.helpers;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import tweet.com.mytweet.models.Tweet;
import tweet.com.mytweet.models.User;

public interface TweetService
{
    @GET("/api/users")
    Call<List<User>> getAllUsers();

    @GET("/api/users/{id}")
    Call<User> getUser(@Path("id") String id);

    @PUT("/api/users")
    Call<User> updateUser(@Body User user);

    @POST("/api/tweets")
    Call<Tweet> addTweet(@Body Tweet tweet);

    @GET("/api/tweets")
    Call<List<Tweet>> getTweets();

    @GET("/api/users/{id}/tweets")
    Call<List<Tweet>> getUsersTweets(@Path("id") String id);

    @GET("/api/tweets/followed")
    Call<List<Tweet>> getFollowedTweets();
}
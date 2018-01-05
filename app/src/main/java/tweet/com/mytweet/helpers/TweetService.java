package tweet.com.mytweet.helpers;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import tweet.com.mytweet.models.Tweet;
import tweet.com.mytweet.models.User;

public interface TweetService
{
    @GET("/api/users")
    Call<List<User>> getAllUsers();

    @GET("/api/users/{id}")
    Call<User> getUser(@Path("id") String id);

    @GET("/api/tweets")
    Call<List<Tweet>> getTweets();

    @GET("/api/tweets/followed")
    Call<List<Tweet>> getFollowedTweets();
}
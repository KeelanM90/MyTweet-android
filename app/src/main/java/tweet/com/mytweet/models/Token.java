package tweet.com.mytweet.models;

/**
 * Created by keela on 05/01/2018.
 */

public class Token {
    public boolean success;
    public String token;
    public User user;

    public Token(boolean success, String token){
        this.success = success;
        this.token = token;
    }
}

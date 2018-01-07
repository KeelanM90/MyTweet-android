package tweet.com.mytweet.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * Created by keela on 01/11/2017.
 *
 * reference: All aspects of this app are heavily based around the below tutorials with parts of the original code remaining
 * https://wit-ictskills-2017.github.io/mobile-app-dev/labwall.html
 */

public class User {
    public String _id;
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public boolean isFollowed = false;

    private static final String JSON_ID = "_id";
    private static final String JSON_FIRST_NAME = "firstname";
    private static final String JSON_LAST_NAME = "lastname";
    private static final String JSON_EMAIL = "email";
    private static final String JSON_PASSWORD = "password";

    public User(String email, String password) {
        this.firstName = "";
        this.lastName = "";
        this.email = email;
        this.password = password;
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User(User user) {
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.email = user.email;
        this.password = user.password;
    }

    public User(JSONObject json) throws JSONException {
        _id = json.getString(JSON_ID);
        firstName = json.getString(JSON_FIRST_NAME);
        lastName = json.getString(JSON_LAST_NAME);
        email = json.getString(JSON_EMAIL);
        password = json.getString(JSON_PASSWORD);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, _id);
        json.put(JSON_FIRST_NAME, firstName);
        json.put(JSON_LAST_NAME, lastName);
        json.put(JSON_EMAIL, email);
        json.put(JSON_PASSWORD, password);
        return json;
    }
}

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
    public Long id;
    public String firstName = "";
    public String lastName = "";
    public String email = "";
    public String password = "";

    private static final String JSON_ID = "id";
    private static final String JSON_FIRST_NAME = "firstname";
    private static final String JSON_LAST_NAME = "lastname";
    private static final String JSON_EMAIL = "email";
    private static final String JSON_PASSWORD = "password";

    public User(String firstName, String lastName, String email, String password) {
        id = unsignedLong();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User(JSONObject json) throws JSONException {
        id = json.getLong(JSON_ID);
        firstName = json.getString(JSON_FIRST_NAME);
        lastName = json.getString(JSON_LAST_NAME);
        email = json.getString(JSON_EMAIL);
        password = json.getString(JSON_PASSWORD);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, Long.toString(id));
        json.put(JSON_FIRST_NAME, firstName);
        json.put(JSON_LAST_NAME, lastName);
        json.put(JSON_EMAIL, email);
        json.put(JSON_PASSWORD, password);
        return json;
    }

    /**
     * Src: Android labs - https://wit-ictskills-2017.github.io/mobile-app-dev/topic03-a/book-c-myrent-01%20(Widgets)/index.html#/05
     * Generate a long greater than zero
     *
     * @return Unsigned Long value greater than zero
     */
    private Long unsignedLong() {
        long rndVal = 0;
        do {
            rndVal = new Random().nextLong();
        } while (rndVal <= 0);
        return rndVal;
    }
}

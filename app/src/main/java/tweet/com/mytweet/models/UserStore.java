package tweet.com.mytweet.models;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by keela on 01/11/2017.
 */

public class UserStore {
    private UserSerializer userSerializer;
    public ArrayList<User> users;

    public UserStore(UserSerializer userSerializer) {

        this.userSerializer = userSerializer;
        try
        {
            users = userSerializer.loadUsers();
        }
        catch (Exception e) {
            users = new ArrayList<>();
        }
    }

    public void addUser(User user) {
        users.add(user);
        this.saveUsers();
    }

    public User getUser(Long id) {
        Log.i(this.getClass().getSimpleName(), "Long parameter id: " + id);

        for (User user : users) {
            if (id.equals(user.id)) {
                return user;
            }
        }
        return null;
    }

    public User getUserByEmail(String email) {
        Log.i(this.getClass().getSimpleName(), "Email: " + email);

        for (User user : users) {
            if (email.equals(user.email)) {
                return user;
            }
        }
        return null;
    }

    public boolean saveUsers()
    {
        try
        {
            userSerializer.saveUsers(users);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}

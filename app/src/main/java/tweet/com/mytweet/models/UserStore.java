package tweet.com.mytweet.models;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by keela on 01/11/2017.
 *
 * reference: All aspects of this app are heavily based around the below tutorials with parts of the original code remaining
 * https://wit-ictskills-2017.github.io/mobile-app-dev/labwall.html
 */

public class UserStore {
    private UserSerializer userSerializer;
    public ArrayList<User> users;

    public UserStore(UserSerializer userSerializer) {

        this.userSerializer = userSerializer;
        try {
            users = userSerializer.loadUsers();
        } catch (Exception e) {
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
            if (id.equals(user._id)) {
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

    public boolean saveUsers() {
        try {
            userSerializer.saveUsers(users);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public  void setUsers(ArrayList<User> newUsers) {
        this.users = new ArrayList<>(newUsers);
        saveUsers();
    }

    public void refresh() {
        try
        {
            users = userSerializer.loadUsers();
        }
        catch (Exception e) {
            users = new ArrayList<>();
        }
    }
}

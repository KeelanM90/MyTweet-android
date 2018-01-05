package tweet.com.mytweet.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import tweet.com.mytweet.R;
import tweet.com.mytweet.app.MyTweetApp;
import tweet.com.mytweet.models.User;

/**
 * Created by keela on 02/11/2017.
 */

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SharedPreferences prefs;
    MyTweetApp app = MyTweetApp.getApp();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        prefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        /**
        User user = app.userStore.getUser(app.loggedInUser.id);
        if (key.equals("email")) {
            user.email = sharedPreferences.getString(key, "");
        } else if (key.equals("password")) {
            user.password = sharedPreferences.getString(key, "");
        } else if (key.equals("firstname")) {
            user.firstName = sharedPreferences.getString(key, "");
        } else if (key.equals("lastname")) {
            user.lastName = sharedPreferences.getString(key, "");
        }
         */
        app.userStore.saveUsers();
    }
}

package tweet.com.mytweet.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import tweet.com.mytweet.R;

/**
 * Created by keela on 02/11/2017.
 */

public class SettingsFragment extends PreferenceFragment
{

    private SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        prefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
    }

    @Override
    public void onStart()
    {
        super.onStart();
        prefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
    }
}

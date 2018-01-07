package tweet.com.mytweet.fragments;

import android.support.v4.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tweet.com.mytweet.R;
import tweet.com.mytweet.app.MyTweetApp;
import tweet.com.mytweet.models.User;
import tweet.com.mytweet.models.UserStore;

/**
 * Created by keela on 07/01/2018.
 */

public class UsersFragment extends ListFragment implements Callback<List<User>> {
    private ArrayList<User> users;
    private UserStore userStore;
    private UserlistAdapter adapter;
    private MyTweetApp app;

    protected SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.app_name);

        app = MyTweetApp.getApp();
        userStore = app.userStore;
        users = userStore.users;

        adapter = new UserlistAdapter(getActivity(), users);
        setListAdapter(adapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.swipe_refresh,
                parent, false);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTweets();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getTweets();
    }

    public void getTweets() {
        Call<List<User>> call = (Call<List<User>>) app.tweetService.getAllUsers();
        call.enqueue(this);
    }

    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
        users.clear();
        for (User user : response.body()) {
            users.add(user);
        }

        userStore.setUsers(users);
        userStore.refresh();

        adapter.notifyDataSetChanged();
        app.tweetServiceAvailable = true;

        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onFailure(Call<List<User>> call, Throwable t) {
        Toast toast = Toast.makeText(getActivity(), "Connection error, showing saved users", Toast.LENGTH_SHORT);
        toast.show();
        app.tweetServiceAvailable = false;
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}

class UserlistAdapter extends ArrayAdapter<User> {
    private Context context;

    public UserlistAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final tweet.com.mytweet.fragments.UserlistAdapter.ViewHolder holder;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.user_view, null);
            holder = new tweet.com.mytweet.fragments.UserlistAdapter.ViewHolder();

            holder.userTextView = (TextView) convertView.findViewById(R.id.userTextView);
            convertView.setTag(holder);
        } else {
            holder = (tweet.com.mytweet.fragments.UserlistAdapter.ViewHolder) convertView.getTag();
        }

        User user = getItem(position);

        holder.userTextView.setText(user.firstName + " " + user.lastName);

        return convertView;
    }

    static class ViewHolder {
        public TextView userTextView;
    }

}


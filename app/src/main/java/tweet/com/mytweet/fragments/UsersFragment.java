package tweet.com.mytweet.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tweet.com.mytweet.R;
import tweet.com.mytweet.app.MyTweetApp;
import tweet.com.mytweet.models.Relationship;
import tweet.com.mytweet.models.User;
import tweet.com.mytweet.models.UserStore;

/**
 * Created by keela on 07/01/2018.
 */

public class UsersFragment extends ListFragment implements OnItemClickListener, Callback<List<User>> {
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


        //adapter = new UserlistAdapter(getActivity(), updateIsFollowing(users));
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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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

    ArrayList<User> updateIsFollowing(ArrayList<User> users) {
        ArrayList<User> adaptedArray = new ArrayList<User>();
        final ArrayList<User> following = new ArrayList<User>();
        Call<List<User>> call = (Call<List<User>>) app.tweetService.getFollowing(app.currentUser._id);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                for (User user : response.body()) {
                    following.add(user);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getContext(), "Could not get followers" + t.toString(), Toast.LENGTH_LONG).show();
            }
        });

        for (User user : users) {
            user.isFollowed = false;
            for (User follow : following){
                if (true) {
                    user.isFollowed = true;
                }
            }
            adaptedArray.add(user);
        }
        return adaptedArray;
    }
}

class UserlistAdapter extends ArrayAdapter<User> {
    private Context context;
    private MyTweetApp app;

    public UserlistAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);
        this.context = context;
        app = MyTweetApp.getApp();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final tweet.com.mytweet.fragments.UserlistAdapter.ViewHolder holder;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.user_view, null);
            holder = new tweet.com.mytweet.fragments.UserlistAdapter.ViewHolder();

            holder.userTextView = (TextView) convertView.findViewById(R.id.userTextView);
            holder.followButton = (Button) convertView.findViewById(R.id.followButton);
            convertView.setTag(holder);
        } else {
            holder = (tweet.com.mytweet.fragments.UserlistAdapter.ViewHolder) convertView.getTag();
        }

        final User user = getItem(position);

        holder.followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.isFollowed) {
                    Call<Relationship> call = (Call<Relationship>) app.tweetService.unfollow(user._id);

                    call.enqueue(new Callback<Relationship>() {
                        @Override
                        public void onResponse(Call<Relationship> call, Response<Relationship> response) {
                            holder.followButton.setBackgroundResource(R.drawable.button_background);
                            holder.followButton.setText("Follow");
                            user.isFollowed = false;
                        }

                        @Override
                        public void onFailure(Call<Relationship> call, Throwable t) {
                            Toast.makeText(context, "Could not follow", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Relationship relationship = new Relationship();
                    relationship.followee = user._id;
                    Call<Relationship> call = (Call<Relationship>) app.tweetService.follow(relationship);

                    call.enqueue(new Callback<Relationship>() {
                        @Override
                        public void onResponse(Call<Relationship> call, Response<Relationship> response) {
                            holder.followButton.setBackgroundResource(R.drawable.unfollow_background);
                            holder.followButton.setText("Unfollow");
                            user.isFollowed = true;
                        }

                        @Override
                        public void onFailure(Call<Relationship> call, Throwable t) {
                            Toast.makeText(context, "Could not follow", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        holder.userTextView.setText(user.firstName + " " + user.lastName);

        return convertView;
    }

    static class ViewHolder {
        public TextView userTextView;
        public Button followButton;
    }

}


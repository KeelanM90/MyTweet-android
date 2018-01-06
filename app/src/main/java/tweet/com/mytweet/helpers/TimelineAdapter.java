package tweet.com.mytweet.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import tweet.com.mytweet.R;
import tweet.com.mytweet.models.Tweet;

/**
 * Created by keela on 06/01/2018.
 */

public class TimelineAdapter extends ArrayAdapter<Tweet> {
    private Context context;

    public TimelineAdapter(Context context, ArrayList<Tweet> tweets) {
        super(context, 0, tweets);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.tweet_view, null);
        }
        Tweet tweet = getItem(position);

        TextView tweetBody = (TextView) convertView.findViewById(R.id.tweetBody);
        TextView tweeterTextView = (TextView) convertView.findViewById(R.id.tweeter);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.tweetDate);

        tweetBody.setText(tweet.getTweetMessage());
        tweeterTextView.setText(tweet.tweeterName);
        dateTextView.setText(tweet.getDateString());

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);

        if (!tweet.img.equals("")) {
            Picasso.with(getContext()).load(tweet.img).resize(500, 0).into(imageView);
        }
        return convertView;
    }
}
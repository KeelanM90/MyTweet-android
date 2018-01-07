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
        final ViewHolder holder;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.tweet_view, null);
            holder = new ViewHolder();

            holder.tweetBody = (TextView) convertView.findViewById(R.id.tweetBody);
            holder.tweeterTextView = (TextView) convertView.findViewById(R.id.tweeter);
            holder.dateTextView = (TextView) convertView.findViewById(R.id.tweetDate);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        Picasso.with(getContext()).cancelRequest(holder.imageView);
        Tweet tweet = getItem(position);

        holder.tweetBody.setText(tweet.getTweetMessage());
        holder.tweeterTextView.setText(tweet.tweeterName);
        holder.dateTextView.setText(tweet.getDateString());

        if (!tweet.img.equals("")) {
            Picasso.with(getContext()).load(tweet.img).resize(500, 0).into(holder.imageView);
        } else {
            Picasso.with(getContext()).load((String) null).into(holder.imageView);
        }

        return convertView;
    }

    static class ViewHolder {
        public TextView tweetBody;
        public TextView tweeterTextView;
        public TextView dateTextView;
        public ImageView imageView;
    }

}


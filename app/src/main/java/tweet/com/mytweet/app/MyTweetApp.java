package tweet.com.mytweet.app;

import android.app.Application;
import tweet.com.mytweet.models.Timeline;

/**
 * Created by keela on 01/11/2017.
 */

public class MyTweetApp extends Application
{
    public Timeline timeline;

    @Override
    public void onCreate()
    {
        super.onCreate();
        timeline = new Timeline();
    }
}

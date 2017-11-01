package tweet.com.mytweet.models;

import java.util.Date;
import java.util.Random;

/**
 * Created by keela on 01/11/2017.
 */

public class Tweet {
    public Long id;
    public Long date;
    private String tweetMessage;

    public Tweet()
    {
        id = unsignedLong();
        date = new Date().getTime();
    }

    /**
     * Src: Android labs - https://wit-ictskills-2017.github.io/mobile-app-dev/topic03-a/book-c-myrent-01%20(Widgets)/index.html#/05
     * Generate a long greater than zero
     * @return Unsigned Long value greater than zero
     */
    private Long unsignedLong() {
        long rndVal = 0;
        do {
            rndVal = new Random().nextLong();
        } while (rndVal <= 0);
        return rndVal;
    }

    public void setTweetMessage(String tweetMessage)
    {
        this.tweetMessage = tweetMessage;
    }

    public String getTweetMessage()
    {
        return tweetMessage;
    }

    public String getDateString() {
        return dateString();
    }

    private String dateString() {
        String dateFormat = "EEE d MMM yyyy H:mm";
        return android.text.format.DateFormat.format(dateFormat, date).toString();
    }
}

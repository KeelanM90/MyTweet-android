package tweet.com.mytweet.helpers;

import android.app.Activity;
import android.content.Intent;

import java.io.Serializable;

/**
 * Created by keela on 01/11/2017.
 *
 * src: https://wit-ictskills-2017.github.io/mobile-app-dev/topic04-a/book-a-myrent-03%20(Action%20Bar%20&%20Dialogs)/index.html#/03
 */

public class IntentHelper
{
    public static void startActivity (Activity parent, Class classname)
    {
        Intent intent = new Intent(parent, classname);
        parent.startActivity(intent);
    }

    public static void startActivityWithData (Activity parent, Class classname, String extraID, Serializable extraData)
    {
        Intent intent = new Intent(parent, classname);
        intent.putExtra(extraID, extraData);
        parent.startActivity(intent);
    }

    public static void startActivityWithDataForResult (Activity parent, Class classname, String extraID, Serializable extraData, int idForResult)
    {
        Intent intent = new Intent(parent, classname);
        intent.putExtra(extraID, extraData);
        parent.startActivityForResult(intent, idForResult);
    }
}
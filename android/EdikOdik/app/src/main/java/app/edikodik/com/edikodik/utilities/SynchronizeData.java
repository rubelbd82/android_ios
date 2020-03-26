package app.edikodik.com.edikodik.utilities;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.widget.Toast;

import app.edikodik.com.edikodik.entities.Categories;
import app.edikodik.com.edikodik.network.NetworkUtility;
import app.edikodik.com.edikodik.services.HomeService;

/**
 * Created by farhadrubel on 6/4/16.
 */

public class SynchronizeData {
    Activity activity;
    Handler.Callback callback;
    public boolean firstRun = false;

    long dayDifference = 86400 * 1;

    public SynchronizeData (Activity activity, Handler.Callback callback) {
        this.activity = activity;
        this.callback = callback;
    }

    public void sync() {
        long currentTime = System.currentTimeMillis() / 1000;
        long updateTime = AppPreferences.getLastUpdate(activity.getApplicationContext());

        if (currentTime - updateTime > dayDifference) {
            HomeService service = new HomeService(activity.getApplicationContext(), callback, false);
            service.execute();
        }
    }

    public boolean firstTimeRun () {
        long timeInMilliSeconds = AppPreferences.getLastUpdate(activity.getApplicationContext());

        if (timeInMilliSeconds == 0) {
            firstRun = true;
            return true;
        }

        return false;
    }


}
package app.edikodik.com.edikodik.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import app.edikodik.com.edikodik.entities.Location;

/**
 * Created by farhadrubel on 6/4/16.
 */

public class AppPreferences {
    private  static  String PREFS_NAME = "AppPreferences";

    // Login


    // Sync
    private static  String KEY_LAST_UPDATE = "last_update";

    // Location
    private static  String KEY_LOCATION_LATITUDE = "location_latitude";
    private static  String KEY_LOCATION_LONGITUDE = "location_longitude";
    private static  String KEY_LOCATION_NAME = "location_name";


    public static void setLastUpdate(Context context, long timeInMilliSeconds) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().putLong(KEY_LAST_UPDATE, timeInMilliSeconds).commit();
    }
    public static long getLastUpdate(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getLong(KEY_LAST_UPDATE, 0l);
    }


    public static void setLocation(Context context, Location location) {
        SharedPreferences.Editor prefEditor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();

        prefEditor.putLong(KEY_LOCATION_LATITUDE, Double.doubleToLongBits(location.getLatitude()));
        prefEditor.putLong(KEY_LOCATION_LONGITUDE, Double.doubleToLongBits(location.getLongitude()));
        prefEditor.putString(KEY_LOCATION_NAME, location.getLocationName());

        prefEditor.commit();
    }

    public static Location getLocation(Context context) {
        Location location = new Location();

        location.setLatitude(Double.longBitsToDouble(context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getLong(KEY_LOCATION_LATITUDE, 0l)));
        location.setLongitude(Double.longBitsToDouble(context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getLong(KEY_LOCATION_LONGITUDE, 0l)));
        location.setLocationName(context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(KEY_LOCATION_NAME, ""));

        if (location.getLatitude() == 0 || location.getLongitude() == 0 || location.getLocationName().isEmpty()) {
            return null;
        }
        return location;
    }
}

package app.edikodik.com.edikodik.utilities;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * Created by farhadrubel on 6/19/16.
 */

public class LocationUtility {

    int MAX_RESULT = 1;

    public Address getLatLngFromLocationName(Context context, String location) {
        if (location != null) {

            Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);

            try {
                /**
                 * Geocoder.getFromLocation - Returns an array of Addresses
                 * that are known to describe the area immediately surrounding the given latitude and longitude.
                 */

                List<Address> addresses = geocoder.getFromLocationName(location, MAX_RESULT);
                return addresses.get(0);
            } catch (IOException e) {
                //e.printStackTrace();
                Log.e(TAG, "Impossible to connect to Geocoder", e);
            }
        }

        return null;
    }
}

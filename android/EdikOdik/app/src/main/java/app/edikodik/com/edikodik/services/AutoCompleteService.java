package app.edikodik.com.edikodik.services;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.edikodik.com.edikodik.entities.Location;
import app.edikodik.com.edikodik.entities.autocomplete.AutoCompleteResponse;
import app.edikodik.com.edikodik.network.NetworkUtility;
import app.edikodik.com.edikodik.utilities.AppPreferences;
import app.edikodik.com.edikodik.utilities.Constants;

/**
 * Created by farhadrubel on 11/23/15.
 */
public class AutoCompleteService {
    public ArrayList<AutoCompleteResponse> callAutoCompleteService(Context context, String q) {
        ArrayList<AutoCompleteResponse> autoCompleteResponseList = new ArrayList<>();
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//        nameValuePairs.add(new BasicNameValuePair("q", q));
//        nameValuePairs.add(new BasicNameValuePair("id", AppPreferences.getSessionKey(context)));



        try {
            Location location = AppPreferences.getLocation(context);

            String lat, lng;
            lat = lng = "undefined" ;
            if (location != null) {
                lat = "" + location.getLatitude();
                lng =  "" + location.getLongitude();
            }

            String url = Constants.autoComplete + "zqrxWQlat" + lat + "WQlng" + lng + "WQdst" + "undefined"
                    + "/?term=" + q;

            String response =  NetworkUtility.readSecuredUrlSansFormat(context, url, nameValuePairs);
            Log.d("wid", "response: " + response);

            Type listType = new TypeToken<List<AutoCompleteResponse>>() {}.getType();
            autoCompleteResponseList = new Gson().fromJson(response, listType);

            if (autoCompleteResponseList.size() < 1) {
                return null;
            }

        } catch (Exception e) {
        e.printStackTrace();
    }


    return autoCompleteResponseList;
    }
}

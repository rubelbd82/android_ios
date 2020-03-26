package app.edikodik.com.edikodik.network;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * Created by farhadrubel on 5/23/16.
 */
public class GsonProcessor {
    public static  Object convertToObject (String response, Class returnClass) {
        Gson gsonBuilder = new GsonBuilder().create();
        return gsonBuilder.fromJson(response, returnClass);
    }
}

package app.edikodik.com.edikodik.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

import app.edikodik.com.edikodik.entities.genericsearch.Listings;
import app.edikodik.com.edikodik.entities.moviesearch.MovieSearchResponse;
import app.edikodik.com.edikodik.enums.ServiceType;
import app.edikodik.com.edikodik.network.NetworkUtility;
import app.edikodik.com.edikodik.utilities.CacheDb;
import app.edikodik.com.edikodik.utilities.Constants;

/**
 * Created by farhadrubel on 5/28/16.
 */

public class MovieSearchService extends AsyncTask<Void, Void, MovieSearchResponse> {


    private Context context;
    private Handler.Callback callback;
    private ProgressDialog progressDialog;
    private boolean showDialog;

    public MovieSearchService(Context context, Handler.Callback callback, boolean showDialog) {
        this.context = context;
        this.callback = callback;
        this.showDialog = showDialog;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (showDialog) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Please Wait...");
            progressDialog.setMessage("Getting Movies");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @Override
    protected MovieSearchResponse doInBackground(Void... params) {

//        Void request = params[0];

        List<NameValuePair> nameValuePairs = new ArrayList<>();
        MovieSearchResponse response = new MovieSearchResponse();
        String url = null;
        url = Constants.movieResult;
        response = (MovieSearchResponse) NetworkUtility.readSecuredUrl(context, url, nameValuePairs, MovieSearchResponse.class);
//        Log.d("wid", "Listing Name is: " + response.getListing().getName());

        return response;
    }

    @Override
    protected void onPostExecute(MovieSearchResponse result) {
        super.onPostExecute(result);
        if (showDialog) {
            if (progressDialog != null)
                progressDialog.dismiss();
            progressDialog = null;
        }

        CacheDb.setMovieSearchResponse(result);
        Message message = new Message();
        message.arg1 = ServiceType.MOVIE_SEARCH.ordinal();
        callback.handleMessage(message);
    }

}
package app.edikodik.com.edikodik.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

import app.edikodik.com.edikodik.entities.moviedetail.MovieDetailResponse;
import app.edikodik.com.edikodik.entities.moviesearch.Data;
import app.edikodik.com.edikodik.enums.ServiceType;
import app.edikodik.com.edikodik.network.NetworkUtility;
import app.edikodik.com.edikodik.utilities.CacheDb;
import app.edikodik.com.edikodik.utilities.Constants;

/**
 * Created by farhadrubel on 5/28/16.
 */

public class MovieDetailService extends AsyncTask<Data, Void, MovieDetailResponse> {


    private Context context;
    private Handler.Callback callback;
    private ProgressDialog progressDialog;
    private boolean showDialog;

    public MovieDetailService(Context context, Handler.Callback callback, boolean showDialog) {
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
    protected MovieDetailResponse doInBackground(Data... params) {

        Data request = params[0];

        List<NameValuePair> nameValuePairs = new ArrayList<>();
        MovieDetailResponse response = new MovieDetailResponse();
        String url = null;
        url = Constants.movieDetail + request.getMovieId();
        response = (MovieDetailResponse) NetworkUtility.readSecuredUrl(context, url, nameValuePairs, MovieDetailResponse.class);
//        Log.d("wid", "Listing Name is: " + response.getListing().getName());

        return response;
    }

    @Override
    protected void onPostExecute(MovieDetailResponse result) {
        super.onPostExecute(result);
        if (showDialog) {
            if (progressDialog != null)
                progressDialog.dismiss();
            progressDialog = null;
        }

        CacheDb.setMovieDetailResponse(result);
        Message message = new Message();
        message.arg1 = ServiceType.MOVIE_DETAIL.ordinal();
        callback.handleMessage(message);
    }

}
package app.edikodik.com.edikodik.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import app.edikodik.com.edikodik.entities.booksearch.BookSearchResponse;
import app.edikodik.com.edikodik.entities.genericsearch.GenericSearchResponse;
import app.edikodik.com.edikodik.entities.moviesearch.MovieSearchResponse;
import app.edikodik.com.edikodik.entities.requests.GenericSearchRequest;
import app.edikodik.com.edikodik.enums.ServiceType;
import app.edikodik.com.edikodik.network.NetworkUtility;
import app.edikodik.com.edikodik.utilities.CacheDb;
import app.edikodik.com.edikodik.utilities.Constants;

/**
 * Created by farhadrubel on 5/28/16.
 */

public class GenericSearchServiceBAK extends AsyncTask<GenericSearchRequest, Void, Object> {


    private Context context;
    private Handler.Callback callback;
    private ProgressDialog progressDialog;
    private boolean showDialog;

    public GenericSearchServiceBAK(Context context, Handler.Callback callback, boolean showDialog) {
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
            progressDialog.setMessage("Getting Listings...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @Override
    protected Object doInBackground(GenericSearchRequest... params) {

        GenericSearchRequest request = params[0];


        List<NameValuePair> nameValuePairs = new ArrayList<>();
        // set based on search type 1


        GenericSearchResponse genericSearchResponse;


        String url = null;

        url = Constants.genericSearch;


        // set based on search type 2
        NameValuePair searchTerm = new BasicNameValuePair("SearchTerm", request.getSearchterm());
        NameValuePair district = new BasicNameValuePair("District", request.getDistrict());
        NameValuePair latitude = new BasicNameValuePair("Latitude", "" + request.getLatitude());
        NameValuePair longitude = new BasicNameValuePair("Longitude", "" + request.getLongitude());

        nameValuePairs.add(searchTerm);
//        nameValuePairs.add(district);
        nameValuePairs.add(latitude);
        nameValuePairs.add(longitude);



        // set based on search type 3
        genericSearchResponse = (GenericSearchResponse) NetworkUtility.readSecuredUrl(context, url, nameValuePairs, GenericSearchResponse.class);
//        Log.d("wid", "লিস্টিং নাম: " + genericSearchResponse.getListings()[0].getName());

        return genericSearchResponse;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        if (showDialog) {
            if (progressDialog != null)
                progressDialog.dismiss();
            progressDialog = null;
        }

        Message message = new Message();

        if (result instanceof GenericSearchResponse) {
            CacheDb.setGenericSearchResponse((GenericSearchResponse) result);
            message.arg1 = ServiceType.GENERIC_SEARCH_LISTING.ordinal();
        } else if (result instanceof MovieSearchResponse) {
            CacheDb.setMovieSearchResponse((MovieSearchResponse) result);
            message.arg1 = ServiceType.GENERIC_SEARCH_MOVIE.ordinal();
        } else if (result instanceof BookSearchResponse) {
            CacheDb.setBookSearchResponse((BookSearchResponse) result);
            message.arg1 = ServiceType.GENERIC_SEARCH_BOOK.ordinal();
        }

        callback.handleMessage(message);
    }




}
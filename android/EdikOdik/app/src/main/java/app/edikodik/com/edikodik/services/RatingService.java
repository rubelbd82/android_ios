package app.edikodik.com.edikodik.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

import app.edikodik.com.edikodik.entities.requests.RatingRequest;
import app.edikodik.com.edikodik.enums.ListingType;
import app.edikodik.com.edikodik.enums.ServiceType;
import app.edikodik.com.edikodik.network.NetworkUtility;
import app.edikodik.com.edikodik.utilities.CacheDb;
import app.edikodik.com.edikodik.utilities.Constants;

/**
 * Created by farhadrubel on 5/28/16.
 */

public class RatingService extends AsyncTask<RatingRequest, Void, String> {


    private Context context;
    private Handler.Callback callback;
    private ProgressDialog progressDialog;
    private boolean showDialog;

    public RatingService(Context context, Handler.Callback callback, boolean showDialog) {
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
    protected String doInBackground(RatingRequest... params) {

        RatingRequest request = params[0];

        List<NameValuePair> nameValuePairs = new ArrayList<>();
//        String response = new CmsResponse();

        String url = null;
        if (request.getListingType() == ListingType.GENERIC) {
            url =  Constants.ratingGeneric;
        } else if (request.getListingType() == ListingType.MOVIE) {
            url =  Constants.ratingMovie;
        } else if (request.getListingType() == ListingType.BOOK) {
            url =  Constants.ratingBook;
        }

        url = url  + request.getListingId() + "/" + request.getUserId() + "/" + request.getScore();
        String response =  NetworkUtility.readSecuredUrl(context, url, nameValuePairs);

        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (showDialog) {
            if (progressDialog != null)
                progressDialog.dismiss();
            progressDialog = null;
        }

//        No need to save
//        CacheDb.setCmsResponse(result);
        Message message = new Message();
        message.arg1 = ServiceType.RATING.ordinal();
        message.obj = result;
        callback.handleMessage(message);
    }

}
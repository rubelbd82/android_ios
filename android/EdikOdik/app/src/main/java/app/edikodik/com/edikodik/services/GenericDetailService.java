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

import app.edikodik.com.edikodik.entities.CategoryResponse;
import app.edikodik.com.edikodik.entities.genericdetail.GenericDetailRequest;
import app.edikodik.com.edikodik.entities.genericdetail.GenericDetailResponse;
import app.edikodik.com.edikodik.entities.genericsearch.Listings;
import app.edikodik.com.edikodik.entities.requests.CategoryRequest;
import app.edikodik.com.edikodik.enums.ServiceType;
import app.edikodik.com.edikodik.network.NetworkUtility;
import app.edikodik.com.edikodik.utilities.CacheDb;
import app.edikodik.com.edikodik.utilities.Constants;

/**
 * Created by farhadrubel on 5/28/16.
 */

public class GenericDetailService extends AsyncTask<Listings, Void, GenericDetailResponse> {


    private Context context;
    private Handler.Callback callback;
    private ProgressDialog progressDialog;
    private boolean showDialog;

    public GenericDetailService(Context context, Handler.Callback callback, boolean showDialog) {
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
            progressDialog.setMessage("Getting listing details");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @Override
    protected GenericDetailResponse doInBackground(Listings... params) {

        Listings request = params[0];

        List<NameValuePair> nameValuePairs = new ArrayList<>();
        GenericDetailResponse response = new GenericDetailResponse();
        String url = null;

//            url = Constants.category + categoryRequest.getCatId() + "/" + URLEncoder.encode(categoryRequest.getCatName(), "UTF-8");
            url = Constants.genericDetail + request.getId() + "/" + "Unknown";

        response = (GenericDetailResponse) NetworkUtility.readSecuredUrl(context, url, nameValuePairs, GenericDetailResponse.class);
//        Log.d("wid", "Listing Name is: " + response.getListing().getName());

        return response;
    }

    @Override
    protected void onPostExecute(GenericDetailResponse result) {
        super.onPostExecute(result);
        if (showDialog) {
            if (progressDialog != null)
                progressDialog.dismiss();
            progressDialog = null;
        }

        CacheDb.setGenericDetailResponse(result);
        Message message = new Message();
        message.arg1 = ServiceType.GENERIC_DETAIL.ordinal();
        callback.handleMessage(message);
    }

}
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

import app.edikodik.com.edikodik.entities.cms.CmsResponse;
import app.edikodik.com.edikodik.entities.freelisting.FreeListingResponse;
import app.edikodik.com.edikodik.entities.requests.CmsRequest;
import app.edikodik.com.edikodik.entities.requests.FreeListingRequest;
import app.edikodik.com.edikodik.enums.ServiceType;
import app.edikodik.com.edikodik.network.NetworkUtility;
import app.edikodik.com.edikodik.utilities.CacheDb;
import app.edikodik.com.edikodik.utilities.Constants;

/**
 * Created by farhadrubel on 5/28/16.
 */

public class FreeListingService extends AsyncTask<FreeListingRequest, Void, FreeListingResponse> {


    private Context context;
    private Handler.Callback callback;
    private ProgressDialog progressDialog;
    private boolean showDialog;

    public FreeListingService(Context context, Handler.Callback callback, boolean showDialog) {
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
            progressDialog.setMessage("Submitting listing");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @Override
    protected FreeListingResponse doInBackground(FreeListingRequest... params) {

        FreeListingRequest request = params[0];

        List<NameValuePair> nameValuePairs = new ArrayList<>();

        nameValuePairs.add(new BasicNameValuePair("business", request.getBusiness()));
        nameValuePairs.add(new BasicNameValuePair("business_type", request.getBusiness_type()));
        nameValuePairs.add(new BasicNameValuePair("contact_person", request.getContact_person()));
        nameValuePairs.add(new BasicNameValuePair("email", request.getEmail()));
        nameValuePairs.add(new BasicNameValuePair("address", request.getAddress()));
        nameValuePairs.add(new BasicNameValuePair("mobile", request.getMobile()));

        FreeListingResponse response = new FreeListingResponse();
        String url = Constants.freeListing;
        response = (FreeListingResponse) NetworkUtility.readSecuredUrl(context, url, nameValuePairs, FreeListingResponse.class);
//        Log.d("wid", "Listing Name is: " + response.getListing().getName());

        return response;
    }

    @Override
    protected void onPostExecute(FreeListingResponse result) {
        super.onPostExecute(result);
        if (showDialog) {
            if (progressDialog != null)
                progressDialog.dismiss();
            progressDialog = null;
        }

        CacheDb.setFreeListingResponse(result);
        Message message = new Message();
        message.arg1 = ServiceType.FREE_LISTING.ordinal();
        message.obj = result;
        callback.handleMessage(message);
    }

}
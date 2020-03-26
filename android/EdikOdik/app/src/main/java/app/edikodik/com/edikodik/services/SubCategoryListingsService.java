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

import app.edikodik.com.edikodik.entities.BaseSubcategories;
import app.edikodik.com.edikodik.entities.CategoryResponse;
import app.edikodik.com.edikodik.entities.genericsearch.GenericSearchResponse;
import app.edikodik.com.edikodik.entities.requests.CategoryRequest;
import app.edikodik.com.edikodik.enums.ServiceType;
import app.edikodik.com.edikodik.network.NetworkUtility;
import app.edikodik.com.edikodik.utilities.CacheDb;
import app.edikodik.com.edikodik.utilities.Constants;

/**
 * Created by farhadrubel on 5/28/16.
 */

public class SubCategoryListingsService extends AsyncTask<BaseSubcategories, Void, GenericSearchResponse> {


    private Context context;
    private Handler.Callback callback;
    private ProgressDialog progressDialog;
    private boolean showDialog;

    public SubCategoryListingsService(Context context, Handler.Callback callback, boolean showDialog) {
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
    protected GenericSearchResponse doInBackground(BaseSubcategories... params) {

        BaseSubcategories baseSubcategory = params[0];
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        GenericSearchResponse genericSearchResponse;
        String url = null;

        url = Constants.subCategory + "Unknown/"  + baseSubcategory.getSubCatId() + "/" ;

        genericSearchResponse = (GenericSearchResponse) NetworkUtility.readSecuredUrl(context, url, nameValuePairs, GenericSearchResponse.class);
        Log.d("wid", "লিস্টিং নাম: " + genericSearchResponse.getListings()[0].getName());

        return genericSearchResponse;
    }

    @Override
    protected void onPostExecute(GenericSearchResponse result) {
        super.onPostExecute(result);
        if (showDialog) {
            if (progressDialog != null)
                progressDialog.dismiss();
            progressDialog = null;
        }

        CacheDb.setGenericSearchResponse(result);
        Message message = new Message();
        message.arg1 = ServiceType.SUBCATEGORY_SERVICE.ordinal();
        callback.handleMessage(message);
    }

}
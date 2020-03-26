package app.edikodik.com.edikodik.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

import app.edikodik.com.edikodik.entities.cms.CmsResponse;
import app.edikodik.com.edikodik.entities.requests.CmsRequest;
import app.edikodik.com.edikodik.enums.ServiceType;
import app.edikodik.com.edikodik.network.NetworkUtility;
import app.edikodik.com.edikodik.utilities.CacheDb;
import app.edikodik.com.edikodik.utilities.Constants;

/**
 * Created by farhadrubel on 5/28/16.
 */

public class CmsService extends AsyncTask<CmsRequest, Void, CmsResponse> {


    private Context context;
    private Handler.Callback callback;
    private ProgressDialog progressDialog;
    private boolean showDialog;

    public CmsService(Context context, Handler.Callback callback, boolean showDialog) {
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
            progressDialog.setMessage("Getting content");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @Override
    protected CmsResponse doInBackground(CmsRequest... params) {

        CmsRequest request = params[0];

        List<NameValuePair> nameValuePairs = new ArrayList<>();
        CmsResponse response = new CmsResponse();
        String url = null;
        url = Constants.cms + request.getCmsId();
        response = (CmsResponse) NetworkUtility.readSecuredUrl(context, url, nameValuePairs, CmsResponse.class);
//        Log.d("wid", "Listing Name is: " + response.getListing().getName());

        return response;
    }

    @Override
    protected void onPostExecute(CmsResponse result) {
        super.onPostExecute(result);
        if (showDialog) {
            if (progressDialog != null)
                progressDialog.dismiss();
            progressDialog = null;
        }

        CacheDb.setCmsResponse(result);
        Message message = new Message();
        message.arg1 = ServiceType.CMS.ordinal();
//        message.obj = result;
        callback.handleMessage(message);
    }

}
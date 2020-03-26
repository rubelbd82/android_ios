package app.edikodik.com.edikodik.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.NameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import app.edikodik.com.edikodik.dao.CategoryDao;
import app.edikodik.com.edikodik.dao.DistrictDao;
import app.edikodik.com.edikodik.dao.GenericDao;
import app.edikodik.com.edikodik.entities.Categories;
import app.edikodik.com.edikodik.entities.CategoryResponse;
import app.edikodik.com.edikodik.entities.Districts;
import app.edikodik.com.edikodik.entities.HomeResponse;
import app.edikodik.com.edikodik.entities.requests.CategoryRequest;
import app.edikodik.com.edikodik.enums.ServiceType;
import app.edikodik.com.edikodik.network.NetworkUtility;
import app.edikodik.com.edikodik.utilities.AppPreferences;
import app.edikodik.com.edikodik.utilities.CacheDb;
import app.edikodik.com.edikodik.utilities.Constants;

/**
 * Created by farhadrubel on 5/28/16.
 */

public class CategoryService extends AsyncTask<CategoryRequest, Void, CategoryResponse> {


    private Context context;
    private Handler.Callback callback;
    private ProgressDialog progressDialog;
    private boolean showDialog;

    public CategoryService(Context context, Handler.Callback callback, boolean showDialog) {
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
            progressDialog.setMessage("Getting Categories");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @Override
    protected CategoryResponse doInBackground(CategoryRequest... params) {

        CategoryRequest categoryRequest = params[0];

        List<NameValuePair> nameValuePairs = new ArrayList<>();
        CategoryResponse categoryResponse = new CategoryResponse();
        String url = Constants.category + categoryRequest.getCatId() + "/" + "Unknown";

        categoryResponse = (CategoryResponse) NetworkUtility.readSecuredUrl(context, url, nameValuePairs, CategoryResponse.class);
//        Log.d("wid", "logo is: " + categoryResponse.getSubcategories());

        return categoryResponse;
    }

    @Override
    protected void onPostExecute(CategoryResponse result) {
        super.onPostExecute(result);
        if (showDialog) {
            if (progressDialog != null)
                progressDialog.dismiss();
            progressDialog = null;
        }

        CacheDb.setCategoryResponse(result);
        Message message = new Message();
        message.arg1 = ServiceType.CATEGORY_SERVICE.ordinal();
        callback.handleMessage(message);
    }

}
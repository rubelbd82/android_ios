package app.edikodik.com.edikodik.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import app.edikodik.com.edikodik.entities.login.LoginResponse;
import app.edikodik.com.edikodik.entities.requests.LoginRequest;
import app.edikodik.com.edikodik.enums.ServiceType;
import app.edikodik.com.edikodik.network.NetworkUtility;
import app.edikodik.com.edikodik.utilities.CacheDb;
import app.edikodik.com.edikodik.utilities.CommonUtilities;
import app.edikodik.com.edikodik.utilities.Constants;

/**
 * Created by farhadrubel on 5/28/16.
 */

public class LoginService extends AsyncTask<LoginRequest, Void, LoginResponse> {


    private Context context;
    private Handler.Callback callback;
    private ProgressDialog progressDialog;
    private boolean showDialog;

    public LoginService(Context context, Handler.Callback callback, boolean showDialog) {
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
            progressDialog.setMessage("Validating username and password");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @Override
    protected LoginResponse doInBackground(LoginRequest... params) {

        LoginRequest request = params[0];

        List<NameValuePair> nameValuePairs = new ArrayList<>();

        nameValuePairs.add(new BasicNameValuePair("username", request.getUsername()));
        nameValuePairs.add(new BasicNameValuePair("password", request.getPassword()));
        nameValuePairs.add(new BasicNameValuePair("udid", request.getUdid()));
        LoginResponse LoginResponse = new LoginResponse();
        String url = null;

            url = Constants.signin;

        LoginResponse = (LoginResponse) NetworkUtility.readSecuredUrl(context, url, nameValuePairs, LoginResponse.class);

        return LoginResponse;
    }

    @Override
    protected void onPostExecute(LoginResponse result) {
        super.onPostExecute(result);
        if (showDialog) {
            if (progressDialog != null)
                progressDialog.dismiss();
            progressDialog = null;
        }
        Message message = new Message();
        message.arg1 = ServiceType.LOGIN_SERVICE.ordinal();
        message.obj = result;
        callback.handleMessage(message);
    }

}
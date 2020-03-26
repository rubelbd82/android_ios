package app.edikodik.com.edikodik.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import app.edikodik.com.edikodik.entities.login.LoginResponse;
import app.edikodik.com.edikodik.entities.requests.LoginRequest;
import app.edikodik.com.edikodik.entities.requests.SocialMediaLoginRequest;
import app.edikodik.com.edikodik.enums.ServiceType;
import app.edikodik.com.edikodik.network.NetworkUtility;
import app.edikodik.com.edikodik.utilities.Constants;
import app.edikodik.com.edikodik.utilities.LocalUtils;

/**
 * Created by farhadrubel on 5/28/16.
 */

public class SocialMediaLoginService extends AsyncTask<SocialMediaLoginRequest, Void, LoginResponse> {


    private Context context;
    private Handler.Callback callback;
    private ProgressDialog progressDialog;
    private boolean showDialog;

    public SocialMediaLoginService(Context context, Handler.Callback callback, boolean showDialog) {
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
    protected LoginResponse doInBackground(SocialMediaLoginRequest... params) {

        SocialMediaLoginRequest request = params[0];

        List<NameValuePair> nameValuePairs = new ArrayList<>();

        if (!LocalUtils.emptyString(request.getEmail())) {
            nameValuePairs.add(new BasicNameValuePair("email", request.getEmail()));
        }

        if (!LocalUtils.emptyString(request.getDisplayName())) {
            nameValuePairs.add(new BasicNameValuePair("displayName", request.getDisplayName()));
        }

        if (!LocalUtils.emptyString(request.getIdentifier())) {
            nameValuePairs.add(new BasicNameValuePair("identifier", request.getIdentifier()));
        }

        if (!LocalUtils.emptyString(request.getProvider())) {
            nameValuePairs.add(new BasicNameValuePair("provider", request.getProvider()));
        }

        if (!LocalUtils.emptyString(request.getProfileUrl())) {
            String profileUrl =  request.getProfileUrl().replace("\\/", "/");
            nameValuePairs.add(new BasicNameValuePair("profileURL", profileUrl));
        }

        if (!LocalUtils.emptyString(request.getPhotoUrl())) {
            String photoUrl =  request.getPhotoUrl().replace("\\/", "/");
            try {
                photoUrl = java.net.URLDecoder.decode(photoUrl, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            nameValuePairs.add(new BasicNameValuePair("photoURL", photoUrl));
        }

        if (!LocalUtils.emptyString(request.getUdid())) {
            nameValuePairs.add(new BasicNameValuePair("udid", request.getUdid()));
        }

        LoginResponse LoginResponse = new LoginResponse();
        String url = Constants.socialSigning;

        LoginResponse response = (LoginResponse) NetworkUtility.readSecuredUrl(context, url, nameValuePairs, LoginResponse.class);

        return response;
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
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

import app.edikodik.com.edikodik.entities.registration.RegistrationResponse;
import app.edikodik.com.edikodik.entities.requests.RegistrationRequest;
import app.edikodik.com.edikodik.enums.ServiceType;
import app.edikodik.com.edikodik.network.NetworkUtility;
import app.edikodik.com.edikodik.utilities.Constants;

/**
 * Created by farhadrubel on 5/28/16.
 */

public class RegistrationService extends AsyncTask<RegistrationRequest, Void, RegistrationResponse> {


    private Context context;
    private Handler.Callback callback;
    private ProgressDialog progressDialog;
    private boolean showDialog;

    public RegistrationService(Context context, Handler.Callback callback, boolean showDialog) {
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
            progressDialog.setMessage("Registering user");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @Override
    protected RegistrationResponse doInBackground(RegistrationRequest... params) {

        RegistrationRequest request = params[0];

        List<NameValuePair> nameValuePairs = new ArrayList<>();


        NameValuePair password = new BasicNameValuePair("password", request.getPassword());
        NameValuePair email = new BasicNameValuePair("email", request.getUsername());
        NameValuePair district = new BasicNameValuePair("district", request.getDistrict());
        NameValuePair phone = new BasicNameValuePair("phone", request.getPhone());
        NameValuePair firstname = new BasicNameValuePair("firstname", request.getFirstname());
        NameValuePair lastname = new BasicNameValuePair("lastname", request.getLastname());

        nameValuePairs.add(password);
        nameValuePairs.add(email);
        nameValuePairs.add(district);
        nameValuePairs.add(phone);
        nameValuePairs.add(firstname);
        nameValuePairs.add(lastname);

        RegistrationResponse RegistrationResponse = new RegistrationResponse();
        String url = null;

            url = Constants.signUp;

        RegistrationResponse = (RegistrationResponse) NetworkUtility.readSecuredUrl(context, url, nameValuePairs, RegistrationResponse.class);

        return RegistrationResponse;
    }

    @Override
    protected void onPostExecute(RegistrationResponse result) {
        super.onPostExecute(result);
        if (showDialog) {
            if (progressDialog != null)
                progressDialog.dismiss();
            progressDialog = null;
        }
        Message message = new Message();
        message.arg1 = ServiceType.REGISTRATION_SERVICE.ordinal();
        message.obj = result;
        callback.handleMessage(message);
    }

}
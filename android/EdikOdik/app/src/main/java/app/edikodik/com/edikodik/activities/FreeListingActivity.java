package app.edikodik.com.edikodik.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import app.edikodik.com.edikodik.R;
import app.edikodik.com.edikodik.entities.Districts;
import app.edikodik.com.edikodik.entities.freelisting.FreeListingResponse;
import app.edikodik.com.edikodik.entities.registration.RegistrationResponse;
import app.edikodik.com.edikodik.entities.requests.FreeListingRequest;
import app.edikodik.com.edikodik.entities.requests.RegistrationRequest;
import app.edikodik.com.edikodik.enums.ServiceType;
import app.edikodik.com.edikodik.services.FreeListingService;
import app.edikodik.com.edikodik.services.RegistrationService;
import app.edikodik.com.edikodik.utilities.CommonUtilities;

public class FreeListingActivity extends Activity implements Handler.Callback  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_listing);

//        super.initializeView(ActivityType.REGISTER);
//        getSupportActionBar().setTitle("Register");


        this.initializeView();
    }

    private void initializeView() {

    }


    public void actionRegister(View v) {
        register();
    }

    private void register() {
        String business = CommonUtilities.getText(this, R.id.business);
        String business_type = CommonUtilities.getText(this, R.id.business_type);
        String address = CommonUtilities.getText(this, R.id.address);
        String contact_person = CommonUtilities.getText(this, R.id.contact_person);
        String mobile = CommonUtilities.getText(this, R.id.mobile);
        String email = CommonUtilities.getText(this, R.id.email);;

        // validate

        if (business.isEmpty() || business_type.isEmpty() || address.isEmpty() || contact_person.isEmpty() || mobile.isEmpty() ) {
            showFailAlertDialog("All fields are required");
            return;
        }

        FreeListingRequest request = new FreeListingRequest();

        request.setBusiness(business);
        request.setBusiness_type(business_type);
        request.setAddress(address);
        request.setContact_person(contact_person);
        request.setMobile(mobile);
        request.setEmail(email);

        FreeListingService service = new FreeListingService(FreeListingActivity.this, this, true);
        service.execute(request);
    }

    @Override
    public boolean handleMessage(Message message) {

        if (message.arg1 == ServiceType.FREE_LISTING.ordinal()) {
            try {
                FreeListingResponse response = (FreeListingResponse) message.obj;
                if (response.getStatus().equalsIgnoreCase("00")) {
                    showSuccessAlertDialog(response.getMessage());
                } else {
                    showFailAlertDialog(response.getMessage());
                }

            } catch (Exception e) {
                Log.d("wid", "Couldn't parse free listing response");
                showFailAlertDialog("Couldn't validate form. Make sure all required fields are provided");
            }
        }

        return false;
    }


    private void showSuccessAlertDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Success")
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goToHomeScreen();
                    }
                }).show();
    }

    private void showFailAlertDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setNegativeButton("Try again", null)
                .setCancelable(true)
                .setPositiveButton("Home", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goToHomeScreen();
                    }
                }).show();
    }


    public void goToLoginScreen() {
        Intent intent = new Intent(FreeListingActivity.this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void goToHomeScreen() {
        Intent intent = new Intent(FreeListingActivity.this, HomeActivity.class);
        startActivity(intent);
        this.finish();
    }
}

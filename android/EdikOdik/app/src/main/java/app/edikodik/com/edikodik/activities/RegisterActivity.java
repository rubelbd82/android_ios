package app.edikodik.com.edikodik.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import app.edikodik.com.edikodik.R;
import app.edikodik.com.edikodik.entities.Districts;
import app.edikodik.com.edikodik.entities.login.LoginResponse;
import app.edikodik.com.edikodik.entities.registration.RegistrationResponse;
import app.edikodik.com.edikodik.entities.requests.RegistrationRequest;
import app.edikodik.com.edikodik.enums.ActivityType;
import app.edikodik.com.edikodik.enums.ServiceType;
import app.edikodik.com.edikodik.network.NetworkUtility;
import app.edikodik.com.edikodik.services.RegistrationService;
import app.edikodik.com.edikodik.utilities.CommonUtilities;

public class RegisterActivity extends Activity implements Handler.Callback  {
    Spinner spDistricts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        super.initializeView(ActivityType.REGISTER);
//        getSupportActionBar().setTitle("Register");


        this.initializeView();
    }

    private void initializeView() {
        // District
        List<Districts> districts = Districts.listAll(Districts.class);
        ArrayList<Districts> mDatasetDistrict = new ArrayList<>(districts.size());
        mDatasetDistrict.addAll(districts);

        spDistricts = (Spinner) findViewById(R.id.spDistricts);
        ArrayAdapter<Districts> dataAdapter = new ArrayAdapter<Districts>(this,
                android.R.layout.simple_spinner_item, mDatasetDistrict);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDistricts.setAdapter(dataAdapter);
    }


    public void actionRegister(View v) {
        register();
    }

    private void register() {
        if (!NetworkUtility.isNetworkAvailable(this)) {
            CommonUtilities.toast(this, "Internet connection is not available");
            return;
        }

        String username = CommonUtilities.getText(this, R.id.username);
        String password = CommonUtilities.getText(this, R.id.password);
        String firstname = CommonUtilities.getText(this, R.id.firstname);
        String lastname = CommonUtilities.getText(this, R.id.lastname);
        String phone = CommonUtilities.getText(this, R.id.phone);
        String district = CommonUtilities.getSpinnerText(this, R.id.spDistricts);

        // validate

        if (username.isEmpty() || password.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || phone.isEmpty() || district.isEmpty()) {
            showFailAlertDialog("All fields are required");
            return;
        }

        RegistrationRequest request = new RegistrationRequest();
        request.setUsername(username);
        request.setPassword(password);
        request.setFirstname(firstname);
        request.setLastname(lastname);
        request.setPhone(phone);
        request.setDistrict(district);

        RegistrationService service = new RegistrationService(RegisterActivity.this, this, true);
        service.execute(request);
    }

    @Override
    public boolean handleMessage(Message message) {

        if (message.arg1 == ServiceType.REGISTRATION_SERVICE.ordinal()) {
            try {
                RegistrationResponse response = (RegistrationResponse) message.obj;
                if (response.getStatus().equalsIgnoreCase("00")) {
                    showSuccessAlertDialog(response.getMessage());
                } else {
                    showFailAlertDialog(response.getMessage());
                }

            } catch (Exception e) {
                Log.d("wid", "Couldn't parse registration response");
                showFailAlertDialog("Couldn't parse registration response");
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
                        goToLoginScreen();
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
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void goToHomeScreen() {
        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
        startActivity(intent);
        this.finish();
    }
}

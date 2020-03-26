package app.edikodik.com.edikodik.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import app.edikodik.com.edikodik.R;
import app.edikodik.com.edikodik.dao.UserDao;
import app.edikodik.com.edikodik.entities.Categories;
import app.edikodik.com.edikodik.entities.custom.UserModel;
import app.edikodik.com.edikodik.network.NetworkUtility;
import app.edikodik.com.edikodik.utilities.SynchronizeData;

public class SplashActivity extends Activity implements Handler.Callback {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 600;
    boolean firstTimeRun = false;

    SynchronizeData synchronizeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initializeApplication();
    }

    private void initializeApplication() {
        NetworkUtility.getPicassoInstance(this);

        synchronizeData = new SynchronizeData(this, this);

        if (synchronizeData.firstTimeRun()) {
            if (NetworkUtility.isNetworkAvailable(this)) {
                synchronizeData.sync();
            } else {
                showAlertDialog();
                return;
            }
        } else {
            synchronizeData.sync();
            goToHomeScreen();
        }

//        // get category list limit 1
//        // if no category,
//        //     firstInstall = true;
//        //     check internet connection
//        //     if no internet conneciton,
//        //         "You must have internet connection to initialize the application. close"
//        //     else
//        //     async task with postExecution
//
//        // Post execution, if no category again, and first install
//        //     "couldn't get information from server. try again later. Contact us: info@edikodik.com
//
//        // sleep 5 seconds,
//        // Intent go to home page
//
////        goToHomePage();
////        return;
//
//        Categories category =null;
//
//        try {
//            category = Categories.first(Categories.class);
//        } catch (Exception e) {
//            Log.d("wid", "Exception is: " + e.getMessage());
//        }
//
//
//
//        if (category == null) {
//            firstTimeRun = true;
//            if (!NetworkUtility.isNetworkAvailable(this)) {
//                showAlertDialog();
//                return;
//            }
//
//            syncData();
//        } else {
//            if (NetworkUtility.isNetworkAvailable(this)) {
//
//                // shared preferences : if time is greater than 1 day, then try it. //3 //5 //10
//                syncData();
//            }
//            goToHomePage();
//        }

    }

//    private void syncData() {
//        HomeService service = new HomeService(this, this, false);
//        service.execute();
//    }

    private void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("No Internet")
                .setMessage("You must have internet connection to initialize the app for the first time")
                .setNegativeButton("Cancel", null)
                .setCancelable(true)
                .setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        initializeApplication();
                    }
                }).show();
    }


    private void goToHomeScreen(){
        Log.d("wid", "Go to home page called");
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                // If user logged-in, then he or she has to go to home page. else launcher activity.

                Intent intent = null;

                try {
                    UserModel user = new UserDao().getUser();
                    if (user != null) {
                        intent = new Intent(SplashActivity.this, HomeActivity.class);

                    } else {
                        intent = new Intent(SplashActivity.this, LauncherActivity.class);
                    }
                } catch (Exception e) {
                    intent = new Intent(SplashActivity.this, LauncherActivity.class);
                }


//                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (synchronizeData.firstRun) {
            Categories category = Categories.first(Categories.class);
            if (category == null) {
                Toast.makeText(getApplicationContext(), "Couldn't get information from server. try again later. ", Toast.LENGTH_LONG).show();
            } else {
                goToHomeScreen();
            }
        }
        return false;
    }
}
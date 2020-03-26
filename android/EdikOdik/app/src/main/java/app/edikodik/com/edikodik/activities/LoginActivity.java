package app.edikodik.com.edikodik.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import app.edikodik.com.edikodik.R;
import app.edikodik.com.edikodik.dao.UserDao;
import app.edikodik.com.edikodik.entities.custom.UserModel;
import app.edikodik.com.edikodik.entities.login.LoginResponse;
import app.edikodik.com.edikodik.entities.requests.LoginRequest;
import app.edikodik.com.edikodik.entities.requests.SocialMediaLoginRequest;
import app.edikodik.com.edikodik.enums.ServiceType;
import app.edikodik.com.edikodik.network.NetworkUtility;
import app.edikodik.com.edikodik.services.LoginService;
import app.edikodik.com.edikodik.services.SocialMediaLoginService;
import app.edikodik.com.edikodik.utilities.CommonUtilities;
import app.edikodik.com.edikodik.utilities.LocalUtils;

public class LoginActivity extends Activity implements Handler.Callback {

    private static final int RC_SIGN_IN = 10001;
    private static final String TAG = "wid";
    private LoginButton loginButton;
    CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());


        setContentView(R.layout.activity_sign_in);
//        super.initializeView(ActivityType.SIGN_IN);

//        getSupportActionBar().setTitle("Sign In");

        // Setup facebook login

        try {
            UserModel user = new UserDao().getUser();
            if (user != null) {
                if (!LocalUtils.emptyString(user.getSession_id())) {
                    // TODO: Uncomment the following lines
                    goToHomeScreen();
                    return;
                }
            }
        } catch (Exception e) {

        }

        facebookLoginInitialSetup();
        googleLoginInitialSetup();

    }



    private void login() {
        if (!NetworkUtility.isNetworkAvailable(this)) {
            CommonUtilities.toast(this, "Internet connection is not available");
            return;
        }

        String username = CommonUtilities.getText(this, R.id.username);
        String password = CommonUtilities.getText(this, R.id.password);

        if (username.isEmpty() || password.isEmpty()) {
            showFailAlertDialog("Username and password can't be empty");
            return;
        }

        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);
        request.setUdid(CommonUtilities.getUDID(this));
        LoginService service = new LoginService(LoginActivity.this, this, true);
        service.execute(request);
    }



    public void actionLogin(View v) {
        login();
    }

    public void actionGoogleSignin(View v) {
        googleSignIn();
    }

    private void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public boolean handleMessage(Message message) {
        if (message.arg1 == ServiceType.LOGIN_SERVICE.ordinal()) {
            try {
                LoginResponse response = (LoginResponse) message.obj;
                // Save data in database,
                // go to home page
                if (response.getStatus().equalsIgnoreCase("00")) {
                    loginSuccess(response);
                } else {
                    showFailAlertDialog(response.getMessage());
                }

            } catch (Exception e) {
                Log.d("wid", "Couldn't parse login response" + e.getMessage());
                showFailAlertDialog("Couldn't parse login response");
            }
        }
        return false;
    }


    private void loginSuccess(LoginResponse response) {
        saveDataInDatabase(response);
        goToHomeScreen();
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

    private void saveDataInDatabase(LoginResponse response) {
        UserModel userModel = new UserModel();
        userModel.setUsername(response.getUser().getUsername());

        if (response.getUser().getFirstname().isEmpty() && response.getUser().getLastname().isEmpty()) {
            userModel.setFirstname(response.getUser_info().getName());
        } else {
            userModel.setFirstname(response.getUser().getFirstname());
            userModel.setLastname(response.getUser().getLastname());
        }

        userModel.setUserId(response.getUser().getId());
        userModel.setPhone(response.getUser().getPhone());
        userModel.setPhoto(response.getUser_info().getPhoto());
        userModel.setSession_id(response.getSession_id());

        UserDao userDao = new UserDao();

        userDao.saveUser(userModel);
    }

    private void goToHomeScreen() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }

    private void facebookLoginInitialSetup() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");

        // If using in a fragment
//        loginButton.setFragment(this);
        // Other app specific specialization

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code

                Log.d("wid", "Successfully logged-in: Login Result: " + loginResult.getAccessToken());
                graphRequestFacebook(loginResult);
            }

            @Override
            public void onCancel() {
                // App code
                Log.d("wid", "canceled: ");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.d("wid", "Error is: " + exception.getMessage());
            }
        });
    }

    private void graphRequestFacebook(LoginResult loginResult) {
        AccessToken accessToken = loginResult.getAccessToken();
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code

                    Log.d("wid", "RESPONSE (Facebook graph): " + response.getRawResponse());

                        processFacebookGraphResponse(object);
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void processFacebookGraphResponse(JSONObject object) {
        try {
            Log.d("wid", "Id: " + object.getString("id"));
            Log.d("wid", "Name: " + object.getString("name"));
            SocialMediaLoginRequest request = new SocialMediaLoginRequest();

            request.setProvider("Facebook");
            request.setDisplayName(object.getString("name"));
            request.setIdentifier(object.getString("id"));
            request.setEmail(object.getString("email"));
            request.setPhotoUrl("https://graph.facebook.com/"+object.getString("id")+"/picture?type=large");
            request.setProfileUrl(object.getString("link"));
            request.setUdid(CommonUtilities.getUDID(this));

            socialMediaLogin(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Call server
        // Go to


    }

    private void socialMediaLogin(SocialMediaLoginRequest request) {
//        String identifier, displayName, email, provider;
        SocialMediaLoginService service = new SocialMediaLoginService(this, this, true);
        service.execute(request);
    }


    private void googleLoginInitialSetup() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignIn();
            }
        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGoogleSignInResult(result);
        } else {
            Log.d("wid", "Activity result received.: request code: " + requestCode + "result code: " + resultCode + "data" );
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleGoogleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // TODO: finish code
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            acct.getDisplayName();
            acct.getId();

            SocialMediaLoginRequest request = new SocialMediaLoginRequest();
            request.setProvider("Google");
            request.setDisplayName(acct.getDisplayName());
            request.setIdentifier(acct.getId());
            request.setEmail(acct.getEmail());

            request.setPhotoUrl(acct.getPhotoUrl().toString());
            request.setProfileUrl("");
            request.setUdid(CommonUtilities.getUDID(this));

            socialMediaLogin(request);

//            mStatusTextView.showetText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
//            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
//            updateUI(false);
        }
    }



    public boolean isFacebookLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

}

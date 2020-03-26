package app.edikodik.com.edikodik.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

import app.edikodik.com.edikodik.R;
import app.edikodik.com.edikodik.dao.UserDao;
import app.edikodik.com.edikodik.entities.custom.UserModel;
import app.edikodik.com.edikodik.enums.ActivityType;
import app.edikodik.com.edikodik.network.NetworkUtility;
import app.edikodik.com.edikodik.utilities.CircleTransform;
import app.edikodik.com.edikodik.utilities.CommonUtilities;
import app.edikodik.com.edikodik.utilities.Constants;

import static app.edikodik.com.edikodik.R.id.imgDetail;

/**
 * Created by farhadrubel on 6/4/16.
 */

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String SELECTED_ITEM_ID = "selected_item_id";
    Toolbar mToolbar;
    NavigationView mDrawer;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    int mSelectedId = 0;
    boolean mUserSawDrawer = false;
    ImageView imgDrawer;

    ActivityType activityType;
    private CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void initializeView(ActivityType activityType) {
        this.activityType = activityType;
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);

        mDrawer = (NavigationView) findViewById(R.id.main_drawer);
        mDrawer.setNavigationItemSelectedListener(this);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        imgDrawer = (ImageView) findViewById(R.id.imgDrawer);

//        Menu menu = mDrawer.getMenu();
//        menu.getItem(R.id.signin).setVisible(false);
        try {
            UserModel user = new UserDao().getUser();
            if (user != null) {
                CommonUtilities.setText(this, R.id.name, user.getFirstname());

                String userPhoto = user.getPhoto();

                if (!userPhoto.isEmpty()) {
                    Picasso.with(getApplicationContext()).load(user.getPhoto()).placeholder(R.drawable.placeholder).transform(new CircleTransform()).into(imgDrawer);
                } else {
                    Picasso.with(getApplicationContext()).load(R.drawable.placeholder).transform(new CircleTransform()).into(imgDrawer);
                }
                adjustDrawerMenu(true);

            } else {
                drawerWithDefaultValue();
            }
        } catch (Exception e) {
            drawerWithDefaultValue();
        }

        // Initialize Picasso
        NetworkUtility.getPicassoInstance(this);
        
        initializeSocialMediaSdks();

    }

    private void initializeSocialMediaSdks() {

        // Facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    private void drawerWithDefaultValue() {
        CommonUtilities.setText(this, R.id.name, "EdikOdik.com");
        Picasso.with(getApplicationContext()).load(R.drawable.placeholder).placeholder(R.drawable.placeholder).transform(new CircleTransform()).into(imgDrawer);
        adjustDrawerMenu(false);

    }

    private void adjustDrawerMenu(boolean loggedin) {
        if (loggedin) {
            mDrawer.getMenu().findItem(R.id.signin).setVisible(false);
            mDrawer.getMenu().findItem(R.id.signup).setVisible(false);
            mDrawer.getMenu().findItem(R.id.signout).setVisible(true);
        } else {
            mDrawer.getMenu().findItem(R.id.signin).setVisible(true);
            mDrawer.getMenu().findItem(R.id.signup).setVisible(true);
            mDrawer.getMenu().findItem(R.id.signout).setVisible(false);

        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void actionSocialMedia(View v) {

        String tag = (String) v.getTag();
        String url = null;

        if (tag.equalsIgnoreCase("facebook")) {
            url = "http://www.fb.com/EdikOdik";
        }

        if (tag.equalsIgnoreCase("twitter")) {
            url = "https://twitter.com/EdikOdik";
        }

        if (tag.equalsIgnoreCase("pinterest")) {
            url = "https://www.pinterest.com/EdikOdik";
        }

        if (tag.equalsIgnoreCase("google_plus")) {
            url = "https://plus.google.com/u/0/100068479231095637410";
        }

        if (tag.equalsIgnoreCase("instagram")) {
            url = "https://www.instagram.com/edikodik/";
        }

        if (tag.equalsIgnoreCase("youtube")) {
            url = "https://www.youtube.com/user/EdikOdikOfficial";
        }

        if (url == null || url.isEmpty()) {
            CommonUtilities.toast(this, "URL is empty");
            return;
        }

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
//        menuItem.setChecked(true);
        mSelectedId = menuItem.getItemId();

        mDrawerLayout.closeDrawer(GravityCompat.START);
        Intent intent = null;
        if (menuItem.getItemId() == R.id.home) {
            if (activityType != ActivityType.HOME)
                intent = new Intent(this, HomeActivity.class);

        } else if (menuItem.getItemId() == R.id.signin) {
            if (activityType != ActivityType.SIGN_IN)
                intent = new Intent(this, LoginActivity.class);

        } else if (menuItem.getItemId() == R.id.signup) {
            if (activityType != ActivityType.SIGN_UP)
                intent = new Intent(this, RegisterActivity.class);
        } else if (menuItem.getItemId() == R.id.signout) {
            signOut();

            intent = new Intent(this, LauncherActivity.class);
        } else if (menuItem.getItemId() == R.id.about) {
            intent = new Intent(this, CMSActivity.class);
            intent.putExtra("cmsId", Constants.ABOUT);
        } else if (menuItem.getItemId() == R.id.termsandconditions) {
            intent = new Intent(this, CMSActivity.class);
            intent.putExtra("cmsId", Constants.TERMS);
        } else if (menuItem.getItemId() == R.id.contact) {
            intent = new Intent(this, CMSActivity.class);
            intent.putExtra("cmsId", Constants.CONTACT);
        } else if (menuItem.getItemId() == R.id.advertise) {
            intent = new Intent(this, CMSActivity.class);
            intent.putExtra("cmsId", Constants.ADVERTISE);
        } else if (menuItem.getItemId() == R.id.freebusinesslisting) {
            intent = new Intent(this, FreeListingActivity.class);
        }

        if (intent != null)
            startActivity(intent);

        return false;
    }

    @NonNull
    private void signOut() {
        UserDao userDao = new UserDao();
        userDao.removeUsers();
        facebookLogout();
        googleLogout();
    }

    private void facebookLogout() {
        try {
            LoginManager.getInstance().logOut();
        } catch (Exception e) {

        }
    }

    private void googleLogout() {
        try {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            // ...
                        }
                    });

        } catch (Exception e) {

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putInt(SELECTED_ITEM_ID, mSelectedId);
    }

    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}

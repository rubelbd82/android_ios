package app.edikodik.com.edikodik.activities;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.edikodik.com.edikodik.R;
import app.edikodik.com.edikodik.adapters.AutoCompleteAdapter;
import app.edikodik.com.edikodik.adapters.GridViewAdapter;
import app.edikodik.com.edikodik.entities.Categories;
import app.edikodik.com.edikodik.entities.Districts;
import app.edikodik.com.edikodik.entities.Location;
import app.edikodik.com.edikodik.entities.autocomplete.AutoCompleteResponse;
import app.edikodik.com.edikodik.entities.requests.CategoryRequest;
import app.edikodik.com.edikodik.entities.requests.GenericSearchRequest;
import app.edikodik.com.edikodik.enums.ActivityType;
import app.edikodik.com.edikodik.enums.ServiceType;
import app.edikodik.com.edikodik.network.NetworkUtility;
import app.edikodik.com.edikodik.services.BookSearchService;
import app.edikodik.com.edikodik.services.CategoryService;
import app.edikodik.com.edikodik.services.GenericSearchService;
import app.edikodik.com.edikodik.services.MovieSearchService;
import app.edikodik.com.edikodik.utilities.AppPreferences;
import app.edikodik.com.edikodik.utilities.CacheDb;
import app.edikodik.com.edikodik.utilities.CommonUtilities;
import app.edikodik.com.edikodik.utilities.GPSTracker;
import app.edikodik.com.edikodik.utilities.LocalUtils;
import app.edikodik.com.edikodik.utilities.LocationUtility;
import app.edikodik.com.edikodik.utilities.ShareUtility;

public class HomeActivity extends BaseActivity implements Handler.Callback {



    // GridView Adapter
    private GridView gridView;
    private GridViewAdapter gridAdapter;

    private String selectedCategory;

    private Button btnLocation;
    double latitude;
    double longitude;
    String locationName;

    // Auto Complete
    ArrayAdapter<AutoCompleteResponse> adapter;
    AutoCompleteTextView etSearchTerm;

    // District
    Spinner spDistricts;
    ArrayList<Districts> mDatasetDistrict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        super.initializeView(ActivityType.HOME);

        // test ad begins //
//        goToCategory("4", "Restaurant");
        // test ad ends

        // TODO: Uncomment following lines
        this.initializeView();

    }


    private void initializeView() {
        etSearchTerm = (AutoCompleteTextView) findViewById(R.id.searchTerm);

        // Recycle view:
        List<Categories>  categories = Categories.listAll(Categories.class);
        final ArrayList<Categories> mDatasetCategories = new ArrayList<>(categories.size());
        mDatasetCategories.addAll(categories);


        setDistrictListener();

        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, mDatasetCategories);
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("wid", "Clicked item is: " + mDatasetCategories.get(i).getCatName());
                goToCategory(mDatasetCategories.get(i).getCatId(), mDatasetCategories.get(i).getCatName());
            }
        });

        // Location
        btnLocation = (Button) findViewById(R.id.btnLocation);

        Location location = AppPreferences.getLocation(this);

        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            locationName = location.getLocationName();
            btnLocation.setText(locationName);
        }

        // Auto Complete
        adapter = new AutoCompleteAdapter(this, android.R.layout.simple_dropdown_item_1line);
        etSearchTerm.setAdapter(adapter);

        etSearchTerm.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_NULL || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    search();
                    handled = true;
                }
                return handled;
            }
        });
    }


    public void goToCategory(String catId, String catName) {
        if (!NetworkUtility.isNetworkAvailable(this)) {
            CommonUtilities.toast(this, "No internet connection");
            return;
        }

        selectedCategory = catName;

        if (catId.equalsIgnoreCase("36") || catId.equalsIgnoreCase("14")) {
            GenericSearchService service = new GenericSearchService(HomeActivity.this, this, true);
            GenericSearchRequest request = new GenericSearchRequest();
            request.setCatId(catId);
            service.execute(request);
            return;
        } else {
            CategoryRequest categoryRequest = new CategoryRequest();
            categoryRequest.setCatId(catId);
            categoryRequest.setCatName(catName);
            CategoryService service = new CategoryService(HomeActivity.this, this, true);
            service.execute(categoryRequest);
        }

//        if (catId.equalsIgnoreCase("36")) {
//            MovieSearchService service = new MovieSearchService(HomeActivity.this, this, true);
//            service.execute();
//            return;
//        }
//
//        if (catId.equalsIgnoreCase("14")) {
//            BookSearchService service = new BookSearchService(HomeActivity.this, this, true);
//            service.execute();
//            return;
//        }

        // Category Section

    }


    // ACTIONS:
    public void actionSearch(View v) {
         search();
    }

    public void actionLocation(View v) {
        showLocationAlertDialog("Enter location");
    }

    private void search() {
        CacheDb.searchTerm = CommonUtilities.getText(this, R.id.searchTerm);
//        String district = CommonUtilities.getSpinnerText(this, R.id.spDistricts);


        if (LocalUtils.emptyString(CacheDb.searchTerm)) {
            showAlertDialog("Enter something to search");
            return;
        }

        GenericSearchRequest request = new GenericSearchRequest();

        request.setSearchterm(CacheDb.searchTerm);

        if (CacheDb.selectedGenericDistrict != null && !CacheDb.selectedGenericDistrict.isEmpty()) {
            request.setDistrict(CacheDb.selectedGenericDistrict);
        }

        if (latitude != 0 && longitude != 0) {
            request.setLatitude(latitude);
            request.setLongitude(longitude);
        }

        request.setPageNumber(1);

        GenericSearchService service = new GenericSearchService(HomeActivity.this, this, true);
        service.execute(request);
    }


    private void setDistrictListener() {

        // District:
        List<Districts> districts = Districts.listAll(Districts.class);
        mDatasetDistrict = new ArrayList<>(districts.size());
        mDatasetDistrict.addAll(districts);

        Districts district = new Districts();
        district.setId(null);
        district.setDistrictName(" -- Select District -- ");
        mDatasetDistrict.add(0, district);

        spDistricts = (Spinner) findViewById(R.id.spDistricts);
        ArrayAdapter<Districts> dataAdapter = new ArrayAdapter<Districts>(this,
                android.R.layout.simple_spinner_item, mDatasetDistrict);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDistricts.setAdapter(dataAdapter);

        spDistricts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CacheDb.selectedGenericDistrict = mDatasetDistrict.get(i).getDistrictId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        LocalUtils.setDistrictSpinnerValue(spDistricts, mDatasetDistrict, CacheDb.selectedGenericDistrict);
    }

    private void navigate(int mSelectedId) {
    }


    // ALERT DIALOGS
    private void showAlertDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton("Ok", null).show();
    }

    private void showLocationAlertDialog(String message) {
        final EditText etLocation = new EditText(getApplicationContext());
        etLocation.setTextColor(Color.DKGRAY);
        etLocation.setHintTextColor(Color.LTGRAY);
        etLocation.setHint(" Enter custom place name");

        new AlertDialog.Builder(this)
                .setTitle("Location")
//                .setMessage(message)
                .setCancelable(true)
                .setView(etLocation)
                .setNegativeButton("My place", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        currentLocation();
                    }
                })
                .setPositiveButton("Custom place", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        customLocation(etLocation.getText().toString());
                    }
                }).show();
    }

    // LOCATIONS

    private void currentLocation() {
        GPSTracker gpsTracker = new GPSTracker(this);

        if (gpsTracker.getIsGPSTrackingEnabled()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            gpsTracker.getAddressLine(getApplicationContext());

            String addressLine = "";

            if (gpsTracker.getAddressLine(getApplicationContext()) != null) {
                addressLine = gpsTracker.getAddressLine(getApplicationContext());
            }

            String postalCode = "";

            if (gpsTracker.getPostalCode(getApplicationContext()) != null) {
                postalCode = gpsTracker.getPostalCode(getApplicationContext());
            }

            locationName = addressLine + ", " + postalCode ;
            btnLocation.setText(locationName);
            saveLocation();

        } else {
            gpsTracker.showSettingsAlert();
        }
    }

    private void customLocation(String customLocation) {
        if (!NetworkUtility.isNetworkAvailable(this)) {
            CommonUtilities.toast(this, "Internet connection is not available");
            return;
        }


        if (customLocation.isEmpty()) {
            CommonUtilities.toast(getApplicationContext(), "Address you provided is not valid or internal server error");
            return;
        }

        // get custom location and set it's value
        LocationUtility locationUtility = new LocationUtility();
        Address address = locationUtility.getLatLngFromLocationName(getApplicationContext(), customLocation);

        if (address == null) {
            CommonUtilities.toast(getApplicationContext(), "Address you provided is not valid");
            return;
        }

        latitude = address.getLatitude();
        longitude = address.getLongitude();
        locationName = address.getAddressLine(0) + ", " + address.getPostalCode();
        btnLocation.setText(locationName);
        saveLocation();

//        address.getAddressLine(1) + ", " + address.getPostalCode() + ", " + address.getAdminArea()
        return;
    }

    private void saveLocation () {
        if (latitude > 0 || longitude > 0 || !locationName.isEmpty()) {
            Location location = new Location();
            location.setLatitude(latitude);
            location.setLongitude(longitude);
            location.setLocationName(locationName);
            AppPreferences.setLocation(this, location);
        }
    }


    @Override
    public boolean handleMessage(Message message) {
        LocalUtils.activityRouter(this, message, CacheDb.searchTerm, selectedCategory);
        return false;
    }



    public static Uri ResourceToUri (Context context, int resID) {

        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                context.getResources().getResourcePackageName(resID) + '/' +
                context.getResources().getResourceTypeName(resID) + '/' +
                context.getResources().getResourceEntryName(resID) );
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_search) {
//            CommonUtilities.toast(this, "Search selected");
//
//            final Dialog dialog = new Dialog(this);
//            dialog.setContentView(R.layout.search_box);
//            dialog.setCancelable(true);
//            dialog.setTitle("Search");
//            dialog.show();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
package app.edikodik.com.edikodik.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.edikodik.com.edikodik.R;
import app.edikodik.com.edikodik.adapters.AutoCompleteAdapter;
import app.edikodik.com.edikodik.adapters.GenericListingsAdapter;
import app.edikodik.com.edikodik.callbacks.OnGenericListingsClickListener;
import app.edikodik.com.edikodik.entities.Districts;
import app.edikodik.com.edikodik.entities.Location;
import app.edikodik.com.edikodik.entities.autocomplete.AutoCompleteResponse;
import app.edikodik.com.edikodik.entities.genericsearch.GenericSearchResponse;
import app.edikodik.com.edikodik.entities.genericsearch.Listings;
import app.edikodik.com.edikodik.entities.requests.GenericSearchRequest;
import app.edikodik.com.edikodik.enums.ActivityType;
import app.edikodik.com.edikodik.network.NetworkUtility;
import app.edikodik.com.edikodik.services.GenericSearchService;
import app.edikodik.com.edikodik.utilities.AppPreferences;
import app.edikodik.com.edikodik.utilities.CacheDb;
import app.edikodik.com.edikodik.utilities.CommonUtilities;
import app.edikodik.com.edikodik.utilities.Constants;
import app.edikodik.com.edikodik.utilities.GPSTracker;
import app.edikodik.com.edikodik.utilities.LocalUtils;
import app.edikodik.com.edikodik.utilities.LocationUtility;
import app.edikodik.com.edikodik.utilities.ShareUtility;

public class GenericSearchResultActivityBAK extends BaseActivity implements Handler.Callback {
    private ArrayList<Listings> listings = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    // District
    Spinner spDistricts;
    ArrayList<Districts> mDatasetDistrict;

    private Button btnLocation;
    double latitude;
    double longitude;
    String locationName;

    Dialog dialog;

    // Auto Complete
    ArrayAdapter<AutoCompleteResponse> adapter;
    AutoCompleteTextView etSearchTerm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_search_result);
        super.initializeView(ActivityType.GENERIC_SEARCH_RESULT);
        Intent intent = getIntent();
        String searchedTerm = intent.getStringExtra("searchedTerm");
        // Set Title in titlebar
        getSupportActionBar().setTitle(searchedTerm);

        initializeView();
    }

    private void initializeView () {


        // Dialogbox starts
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.search_box);
        dialog.setCancelable(true);
        dialog.setTitle("Search");

        etSearchTerm = (AutoCompleteTextView) dialog.findViewById(R.id.searchTerm);
        etSearchTerm.setText(CacheDb.searchTerm);

        // Location
        btnLocation = (Button) dialog.findViewById(R.id.btnLocation);

        Location location = AppPreferences.getLocation(this);

        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            locationName = location.getLocationName();
            btnLocation.setText(locationName);
        }



        Button btnSearch = (Button) dialog.findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLocationAlertDialog("Location Settings");
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });



        // Add districts
        // District:

        setDistrictListener();

        // Auto Complete
        adapter = new AutoCompleteAdapter(this, android.R.layout.simple_dropdown_item_1line);
        etSearchTerm.setAdapter(adapter);

        //////////////////
        // dialogbox ends
        //////////////////
        GenericSearchResponse genericSearchResponse = CacheDb.getGenericSearchResponse();

        // RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Get Subcategories from CacheDb;




        GenericSearchResponse response = CacheDb.getGenericSearchResponse();

        if (response == null) {
            CommonUtilities.toast(this, "No listing found");
            return;
        }

        Listings[] listingsArray = response.getListings();
        this.listings = new ArrayList<Listings>(Arrays.asList(listingsArray));

        // specify an adapter (see also next example)

        mAdapter = new GenericListingsAdapter(this.getApplicationContext(),  listings, new OnGenericListingsClickListener() {
            @Override
            public void onImageClick(Listings listing) {
                goToGenericDetailScreen(listing);
            }

            @Override
            public void onMapClick(Listings listing) {
                map(listing);
            }

            @Override
            public void onShareClick(Listings listing) {
                share(listing);
            }

            @Override
            public void onCallClick(Listings listing) {
                makeCall(listing);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }




    private void search() {
        if (!NetworkUtility.isNetworkAvailable(this)) {
            CommonUtilities.toast(this, "No internet connection");
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            return;
        }

        CacheDb.searchTerm = etSearchTerm.getText().toString();

        if (LocalUtils.emptyString(CacheDb.searchTerm)) {
            CommonUtilities.toast(this, "Enter something to search");
            return;
        }

        GenericSearchRequest request = new GenericSearchRequest();

        request.setSearchterm(CacheDb.searchTerm);

        if (!LocalUtils.emptyString(CacheDb.selectedGenericDistrict)) {
            request.setDistrict(CacheDb.selectedGenericDistrict);
        }

        if (latitude != 0 && longitude != 0) {
            request.setLatitude(latitude);
            request.setLongitude(longitude);
        }

        GenericSearchService service = new GenericSearchService(GenericSearchResultActivityBAK.this, this, true);
        service.execute(request);
    }

    private void goToGenericDetailScreen(Listings listing) {
        CacheDb.setGenericSearchResultListing(listing);

        Intent intent = new Intent(this, GenericDetailActivity.class);
        startActivity(intent);

    }

    private void map(Listings listing) {
        String latitude = listing.getLatitude();
        String longitude = listing.getLongitude();
        if (!latitude.isEmpty() && !longitude.isEmpty()) {
//            String uri = String.format(Locale.ENGLISH, "geo:%s,%s", latitude, longitude);
            String geoUri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude + " (" + listing.getName() + ")";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
            startActivity(intent);
        }
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
        etLocation.setHint("Enter custom place name");

        new AlertDialog.Builder(this)
                .setTitle("Location")
                .setMessage(message)
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
            locationName = gpsTracker.getAddressLine(getApplicationContext()) + ", " + gpsTracker.getPostalCode(getApplicationContext());
            btnLocation.setText(locationName);
            saveLocation();

        } else {
            gpsTracker.showSettingsAlert();
        }

        if (!dialog.isShowing()) {
            dialog.show();
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

        if (!dialog.isShowing()) {
            dialog.show();
        }
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


    private void share(Listings listing) {
//        final Context context =  getApplicationContext();
//        AlertDialog.Builder builder = new AlertDialog.Builder(GenericSearchResultActivity.this);
//        builder.setTitle("Title");
//        builder.setItems(new CharSequence[]
//                        {"button 1", "button 2", "button 3"},
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // The 'which' argument contains the index position
//                        // of the selected item
//                        switch (which) {
//                            case 0:
//                                Toast.makeText(context, "clicked 1", 0).show();
//                                break;
//                            case 1:
//                                Toast.makeText(context, "clicked 2", 0).show();
//                                break;
//                            case 2:
//                                Toast.makeText(context, "clicked 3", 0).show();
//                                break;
//                        }
//                    }
//                });
//        builder.create().show();

        ShareUtility.shareIt(GenericSearchResultActivityBAK.this, Constants.baseUrl + Constants.genericDetail + listing.getId());
    }

    private void makeCall(Listings listing) {
        if (listing.getMobile().isEmpty()) {
            CommonUtilities.toast(this, "No number to call");
            return;
        }

        Intent intent = new Intent(Intent.ACTION_CALL);

        intent.setData(Uri.parse("tel:"+listing.getMobile()));
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setDistrictListener() {
        List<Districts> districts = Districts.listAll(Districts.class);
        mDatasetDistrict = new ArrayList<>(districts.size());
        mDatasetDistrict.addAll(districts);

        spDistricts = (Spinner) dialog.findViewById(R.id.spDistricts);
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


    @Override
    public boolean handleMessage(Message message) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        LocalUtils.activityRouter(this, message, CacheDb.searchTerm, null);
        return false;
    }
}

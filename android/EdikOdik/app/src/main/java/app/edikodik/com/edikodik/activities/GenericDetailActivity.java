package app.edikodik.com.edikodik.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Rating;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Cache;
import com.squareup.picasso.Picasso;

import app.edikodik.com.edikodik.R;
import app.edikodik.com.edikodik.dao.UserDao;
import app.edikodik.com.edikodik.entities.custom.UserModel;
import app.edikodik.com.edikodik.entities.genericdetail.GenericDetailResponse;
import app.edikodik.com.edikodik.entities.genericsearch.Listings;
import app.edikodik.com.edikodik.enums.ActivityType;
import app.edikodik.com.edikodik.enums.ListingType;
import app.edikodik.com.edikodik.enums.ServiceType;
import app.edikodik.com.edikodik.services.GenericDetailService;
import app.edikodik.com.edikodik.utilities.AdvertisementUtility;
import app.edikodik.com.edikodik.utilities.CacheDb;
import app.edikodik.com.edikodik.utilities.CircleTransform;
import app.edikodik.com.edikodik.utilities.CommonUtilities;
import app.edikodik.com.edikodik.utilities.Constants;
import app.edikodik.com.edikodik.utilities.RatingUtility;
import app.edikodik.com.edikodik.utilities.ShareUtility;

public class GenericDetailActivity extends BaseActivity implements Handler.Callback {
    Listings listing;
    ImageView imgDetail;
    RatingBar ratingBar;
    GenericDetailResponse response;

    public FloatingActionButton btnMap, btnShare, btnCall;
    public LinearLayout featured, verified, map, share, call;
    TextView tvDistance;
    Button buyOnline;
    private String targetUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_detail);
        super.initializeView(ActivityType.GENERIC_DETAIL);

        this.getDataFromCachedDb();

        getSupportActionBar().setTitle(listing.getName());

        this.initializeView();
    }

    private void initializeView() {
        imgDetail = (ImageView) findViewById(R.id.imgDetail);

        if (listing.getListingImageLink1() != null && !listing.getListingImageLink1().isEmpty())
            Picasso.with(getApplicationContext()).load(listing.getListingImageLink1()).placeholder(R.drawable.placeholder).into(imgDetail);

        String address = "";

        if (listing.getAddress1() != null && !listing.getAddress1().isEmpty())
            address = address + listing.getAddress1();

        if (listing.getAddress2() != null && !listing.getAddress2().isEmpty())
            address = address + ", " + listing.getAddress2();

        if (listing.getDistrict() != null && !listing.getDistrict().isEmpty())
            address = address + ", " + listing.getDistrict();

        CommonUtilities.setText(this, R.id.tvName, listing.getName());
        CommonUtilities.setText(this, R.id.tvAddress, address);
        CommonUtilities.setText(this, R.id.tvMobile, listing.getMobile());
        CommonUtilities.setText(this, R.id.tvDescription, listing.getDescription());
        CommonUtilities.setText(this, R.id.tvModesOfPayment, listing.getModesOfPayment());
        CommonUtilities.setText(this, R.id.tvEstablished, listing.getEstablished());


        // Set Rating
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        String rating = listing.getRating();
        if (rating != null) {
            ratingBar.setRating(Float.parseFloat(rating));
        }


        // Horizontal bars

        featured = (LinearLayout) findViewById(R.id.featured);
        verified = (LinearLayout) findViewById(R.id.verified);
        map = (LinearLayout) findViewById(R.id.map);
        share = (LinearLayout) findViewById(R.id.share);
        call = (LinearLayout) findViewById(R.id.call);

        btnMap = (FloatingActionButton) findViewById(R.id.btnMap);
        btnShare = (FloatingActionButton) findViewById(R.id.btnShare);
        btnCall = (FloatingActionButton) findViewById(R.id.btnCall);


        String featured = listing.getFeatured();

        if (featured == null || !featured.equalsIgnoreCase("1")) {
            this.featured.setVisibility(View.GONE);
        } else {
            this.featured.setVisibility(View.VISIBLE);
        }

        String verified = listing.getVerified();

        if (verified == null || !verified.equalsIgnoreCase("1")) {
            this.verified.setVisibility(View.GONE);
        } else {
            this.verified.setVisibility(View.VISIBLE);
        }

        String mobile = listing.getMobile();

        if (mobile == null || mobile.isEmpty()) {
            this.call.setVisibility(View.GONE);
        } else {
            this.call.setVisibility(View.VISIBLE);
        }

        tvDistance = (TextView) findViewById(R.id.tvDistance);


        String distanceString = listing.getDistanta();
        if (distanceString != null) {
            float distance = Float.parseFloat(distanceString);
            if (distance > 0 && distance < 100) {

                this.tvDistance.setText(String.format("%.2f", distance) + " km");
            } else {
                this.tvDistance.setText("Distance unavailable");
            }

        } else {
            this.tvDistance.setText("Distance unavailable");
        }


        // Buy online

        buyOnline = (Button) findViewById(R.id.buyOnline);
        String url = listing.getOnlineOrder();

        if (url == null || url.isEmpty()) {
            buyOnline.setVisibility(View.GONE);
        } else {
            buyOnline.setVisibility(View.VISIBLE);
            if (listing.getIsJob() != null && !listing.getIsJob().isEmpty() && listing.getIsJob().equalsIgnoreCase("true")) {
                buyOnline.setText("Apply Online");
            }
        }
        GenericDetailService service = new GenericDetailService(GenericDetailActivity.this, this, false);
        service.execute(listing);

    }

    ///////////////////////////////////
    // Button Actions Start
    //////////////////////////////////

    public void actionTargetUrl() {
        String url = targetUrl;

        if (url == null || url.isEmpty()) {
            CommonUtilities.toast(this, "URL is empty");
            return;
        }

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void actionMap(View v) {
        String latitude = listing.getLatitude();
        String longitude = listing.getLongitude();
        if (!latitude.isEmpty() && !longitude.isEmpty()) {
//            String uri = String.format(Locale.ENGLISH, "geo:%s,%s", latitude, longitude);
            String geoUri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude + " (" + listing.getName() + ")";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
            startActivity(intent);
        }
    }

    public void actionShare(View v) {
        ShareUtility.shareIt(GenericDetailActivity.this, Constants.baseUrl + Constants.genericDetail + listing.getId());
    }

    public void actionCall(View v) {
        if (listing.getMobile().isEmpty()) {
            CommonUtilities.toast(this, "No number to call");
            return;
        }

        Intent intent = new Intent(Intent.ACTION_CALL);

        intent.setData(Uri.parse("tel:"+listing.getMobile()));
        startActivity(intent);
    }

    public void actionBuyOnline(View v) {
        String url = listing.getOnlineOrder();

        if (url == null || url.isEmpty()) {
            CommonUtilities.toast(this, "URL is empty");
            return;
        }

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }


    public void actionRate(View v) {
        RatingUtility ratingUtility = new RatingUtility(this, listing.getId(), ListingType.GENERIC);
        ratingUtility.initialize();
    }



    ///////////////////////////////////
    // Button Actions End
    //////////////////////////////////


    ///////////////////////////////////
    // Dialogs Start
    //////////////////////////////////

    private void showDialogForNonUser() {
        final Context context =  getApplicationContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(GenericDetailActivity.this);
        builder.setTitle("Title");
        builder.setItems(new CharSequence[]
                        {"button 1", "button 2", "button 3"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which) {
                            case 0:
//                                Toast.makeText(context, "clicked 1", 0).show();
                                break;
                            case 1:
//                                Toast.makeText(context, "clicked 2", 0).show();
                                break;
                            case 2:
//                                Toast.makeText(context, "clicked 3", 0).show();
                                break;
                        }
                    }
                });
        builder.create().show();

    }
    ///////////////////////////////////
    // Dialogs End
    //////////////////////////////////


    private void getDataFromCachedDb() {
        listing = CacheDb.getGenericSearchResultListing();
    }

    @Override
    public boolean handleMessage(Message message) {
        if (message.arg1 == ServiceType.GENERIC_DETAIL.ordinal()) {
            response = CacheDb.getGenericDetailResponse();
            String payment =  getStringFromArray(response.getPayment());

            if (payment != null) {
                CommonUtilities.setText(this, R.id.tvModesOfPayment, payment);
            }


            setHoursOfOperation(response.getHours());
            Log.d("wid", "payment: " + payment);
        }
        return false;
    }


    private String getStringFromArray(String[] items) {
       try {
           String result = "";
           for (String item : items) {
               result = result + item + ", ";
           }
           result = result.replaceAll(", $", "");
           return result;
       } catch (Exception e) {
           Log.d("wid", "error: " + e.getMessage());
       }
        return null;
    }


    private void initializeAdvertisement() {
        // Advertisement
        AdvertisementUtility advertisementUtility = new AdvertisementUtility(this);

        if (CacheDb.getGenericDetailResponse() != null) {
            targetUrl = advertisementUtility.setAdvertisement(CacheDb.getGenericDetailResponse().getAds_mobile());
        } else {
            advertisementUtility.setAdvertisement(null);
        }
    }
    private void setHoursOfOperation (String data) {
        data = styles() + data;
        WebView webview = (WebView)this.findViewById(R.id.hoursOfOperation);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadDataWithBaseURL("", data, "text/html", "UTF-8", "");
    }

    private String styles() {
        String style = "<style>" +
                "body {background:#f1efef; color:#787777 }" +
                ".th-left {\n" +
                "            text-align: left;\n" +
                "            font-weight: bold;\n" +
                "            padding-left: 5px;\n" +
                "            color: #f1efef;\n" +
                "            border: #ccc 1px solid;\n" +
                "        }" +
                "" +
                ".highlight-textbox-border {\n" +
                "border:1px solid  #ccc; \n" +
                "font-size: inherit;\n" +
                "}" +
                ".background-header {\n" +
                "\tbackground: #787777;\n" +
                "}" +
                ".th-right {\n" +
                "padding-right: 5px;\n" +
                "text-align: right; \n" +
                "color: #f1efef;\n" +
                "border: #ccc 1px solid;\n" +
                "}\n" +
                "\n"+
                "</style>";

        return style;

    }
}
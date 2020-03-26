package app.edikodik.com.edikodik.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import app.edikodik.com.edikodik.R;
import app.edikodik.com.edikodik.adapters.CinemahallAdapter;
import app.edikodik.com.edikodik.callbacks.OnCinemahallClickListener;
import app.edikodik.com.edikodik.entities.genericsearch.Listings;
import app.edikodik.com.edikodik.entities.moviedetail.MovieDetailResponse;
import app.edikodik.com.edikodik.entities.moviesearch.Data;
import app.edikodik.com.edikodik.enums.ActivityType;
import app.edikodik.com.edikodik.enums.ListingType;
import app.edikodik.com.edikodik.enums.ServiceType;
import app.edikodik.com.edikodik.services.MovieDetailService;
import app.edikodik.com.edikodik.utilities.AdvertisementUtility;
import app.edikodik.com.edikodik.utilities.CacheDb;
import app.edikodik.com.edikodik.utilities.CommonUtilities;
import app.edikodik.com.edikodik.utilities.Constants;
import app.edikodik.com.edikodik.utilities.RatingUtility;
import app.edikodik.com.edikodik.utilities.ShareUtility;

public class MovieDetailActivity extends BaseActivity implements Handler.Callback {
    Data movie;
    MovieDetailResponse response;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ImageView imgDetail;
    RatingBar ratingBar;

    Button buyOnline;

    private String targetUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        super.initializeView(ActivityType.MOVIE_DETAIL);

        getSupportActionBar().setTitle("Movie Detail");

        this.initializeView();
    }

    private void initializeView() {
        // TODO: 6/11/16 Will add functions soon.
        getDataFromCachedDb();

        imgDetail = (ImageView) findViewById(R.id.imgDetail);
        Picasso.with(getApplicationContext()).load(movie.getMovieBookImageLink1()).placeholder(R.drawable.restaurant_1).into(imgDetail);

        CommonUtilities.setText(this, R.id.title, movie.getTitle());
        CommonUtilities.setText(this, R.id.language, movie.getLanguage_Name());
        CommonUtilities.setText(this, R.id.duration, movie.getDuration());
        CommonUtilities.setText(this, R.id.director, movie.getDirector());
        CommonUtilities.setText(this, R.id.producer, movie.getProducer());
        CommonUtilities.setText(this, R.id.releaseDate, movie.getPublishDate());
        CommonUtilities.setText(this, R.id.cast, movie.getCast_Crew());
        CommonUtilities.setText(this, R.id.description, movie.getDescription());

        // Set Rating
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        String rating = movie.getRating();
        if (rating != null) {
            ratingBar.setRating(Float.parseFloat(rating));
        }

        buyOnline = (Button) findViewById(R.id.buyOnline);
        String url = movie.getBuyOnlineLink();

        if (url == null || url.isEmpty()) {
            buyOnline.setVisibility(View.GONE);
        } else {
            buyOnline.setVisibility(View.VISIBLE);
        }

        MovieDetailService service = new MovieDetailService(MovieDetailActivity.this, this, false);
        service.execute(movie);


        // RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
//        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);



    }

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


    public void actionBuyOnline(View v) {
        String url = movie.getBuyOnlineLink();

        if (url == null || url.isEmpty()) {
            CommonUtilities.toast(this, "URL is empty");
            return;
        }

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void actionRate(View v) {
        RatingUtility ratingUtility = new RatingUtility(this, movie.getMovieId(), ListingType.MOVIE);
        ratingUtility.initialize();
    }

    public void actionShare(View v) {
        ShareUtility.shareIt(MovieDetailActivity.this, Constants.baseUrl + Constants.movieDetail + movie.getMovieId());
    }

    private void getDataFromCachedDb() {
        movie = CacheDb.getMovieOfSearchResult();
    }

    @Override
    public boolean handleMessage(Message message) {
        if (message.arg1 == ServiceType.MOVIE_DETAIL.ordinal()) {
            populateView();
        }
        return false;
    }




    private void populateView() {
        // RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        response = CacheDb.getMovieDetailResponse();

        if (response.getListings().length < 1)
            return;

        ArrayList<app.edikodik.com.edikodik.entities.moviedetail.Listings> cinemahallList = new ArrayList<>();

        cinemahallList = addCinemahallsToArray();

        // specify an adapter (see also next example)




        mAdapter = new CinemahallAdapter(getApplicationContext(), cinemahallList, new OnCinemahallClickListener() {

            @Override
            public void onWebsiteClick(Listings listing) {

            }

            @Override
            public void onFacebookClick(Listings listing) {

            }

            @Override
            public void onCallClick(Listings listing) {

            }
        });
        mRecyclerView.setAdapter(mAdapter);

        // Advertisement
        AdvertisementUtility advertisementUtility = new AdvertisementUtility(this);

        if (CacheDb.getMovieDetailResponse().getAds_mobile() != null) {
            targetUrl = advertisementUtility.setAdvertisement(CacheDb.getMovieDetailResponse().getAds_mobile());
        } else {
            advertisementUtility.setAdvertisement(null);
        }
    }


    private ArrayList<app.edikodik.com.edikodik.entities.moviedetail.Listings> addCinemahallsToArray() {
        ArrayList<app.edikodik.com.edikodik.entities.moviedetail.Listings> cinemahallList = new ArrayList<>();
        response = CacheDb.getMovieDetailResponse();

        app.edikodik.com.edikodik.entities.moviedetail.Listings[] cinemahalls = response.getListings();


        for (app.edikodik.com.edikodik.entities.moviedetail.Listings cinemahall : cinemahalls) {
            cinemahallList.add(cinemahall);
            // TODO: REMOVE FOLLOWING REPETITIONS
//            cinemahallList.add(cinemahall);
//            cinemahallList.add(cinemahall);
//            cinemahallList.add(cinemahall);
//            cinemahallList.add(cinemahall);
        }

        return cinemahallList;


    }
}

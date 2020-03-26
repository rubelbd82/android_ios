package app.edikodik.com.edikodik.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.squareup.picasso.Picasso;

import app.edikodik.com.edikodik.R;
import app.edikodik.com.edikodik.entities.bookdetail.BookDetailResponse;
import app.edikodik.com.edikodik.entities.booksearch.Data;
import app.edikodik.com.edikodik.entities.moviedetail.MovieDetailResponse;
import app.edikodik.com.edikodik.enums.ActivityType;
import app.edikodik.com.edikodik.enums.ListingType;
import app.edikodik.com.edikodik.enums.ServiceType;
import app.edikodik.com.edikodik.services.BookDetailService;
import app.edikodik.com.edikodik.utilities.AdvertisementUtility;
import app.edikodik.com.edikodik.utilities.CacheDb;
import app.edikodik.com.edikodik.utilities.CommonUtilities;
import app.edikodik.com.edikodik.utilities.Constants;
import app.edikodik.com.edikodik.utilities.RatingUtility;
import app.edikodik.com.edikodik.utilities.ShareUtility;

public class BookDetailActivity extends BaseActivity implements Handler.Callback {
    Data book;
    BookDetailResponse response;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ImageView imgDetail;
    RatingBar ratingBar;

    private String targetUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        super.initializeView(ActivityType.BOOK_DETAIL);

        getSupportActionBar().setTitle("Book Detail");

        this.initializeView();
    }

    private void initializeView() {
        // TODO: 6/11/16 Will add functions soon.
        getDataFromCachedDb();

        book.getWriter();
        book.getDirector(); // Publisher

        book.getIsbn();
        book.getEdition();
        book.getLanguage();
        book.getPrice();
        book.getGenre_GenreName();
        book.getDescription();


        imgDetail = (ImageView) findViewById(R.id.imgDetail);
        Picasso.with(getApplicationContext()).load(book.getMovieBookImageLink1()).placeholder(R.drawable.restaurant_1).into(imgDetail);




        CommonUtilities.setText(this, R.id.title, book.getTitle());
        CommonUtilities.setText(this, R.id.writer, book.getWriter());
        CommonUtilities.setText(this, R.id.director, book.getDirector());
        CommonUtilities.setText(this, R.id.isbn, book.getIsbn());
        CommonUtilities.setText(this, R.id.edition, book.getEdition());



        CommonUtilities.setText(this, R.id.language, book.getLanguage_Name());
        CommonUtilities.setText(this, R.id.price, book.getPrice());
        CommonUtilities.setText(this, R.id.genre, book.getGenre_GenreName());
        CommonUtilities.setText(this, R.id.description, book.getDescription());

//        CommonUtilities.setText(this, R.id.duration, movie.getDuration());

//        CommonUtilities.setText(this, R.id.producer, movie.getProducer());
//        CommonUtilities.setText(this, R.id.releaseDate, book.getPublishDate());
//        CommonUtilities.setText(this, R.id.cast, movie.getCast_Crew());

        // Set Rating

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        String rating = book.getRating();
        if (rating != null) {
            ratingBar.setRating(Float.parseFloat(rating));
        }
        BookDetailService service = new BookDetailService(BookDetailActivity.this, this, false);
        service.execute(book);
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
        String url = book.getBuyOnlineLink();

        if (url == null || url.isEmpty()) {
            CommonUtilities.toast(this, "URL is empty");
            return;
        }

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void actionRate(View v) {
        RatingUtility ratingUtility = new RatingUtility(this, book.getBookId(), ListingType.BOOK);

        ratingUtility.initialize();
    }

    public void actionShare(View v) {
        Log.d("wid", Constants.baseUrl + Constants.bookDetail + book.getBookId());
        ShareUtility.shareIt(BookDetailActivity.this, Constants.baseUrl + Constants.bookDetail + book.getBookId());
    }

    private void getDataFromCachedDb() {
        book = CacheDb.getBookOfSearchResult();
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

        response = CacheDb.getBookDetailResponse();

        AdvertisementUtility advertisementUtility = new AdvertisementUtility(this);

        if (CacheDb.getCategoryResponse().getAds_mobile() != null) {
            targetUrl = advertisementUtility.setAdvertisement(CacheDb.getCategoryResponse().getAds_mobile());
        } else {
            advertisementUtility.setAdvertisement(null);
        }

    }
}

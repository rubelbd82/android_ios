package app.edikodik.com.edikodik.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import app.edikodik.com.edikodik.R;
import app.edikodik.com.edikodik.adapters.MovieSearchResultAdapter;
import app.edikodik.com.edikodik.callbacks.OnMovieSearchResultClickListener;
import app.edikodik.com.edikodik.entities.Genres;
import app.edikodik.com.edikodik.entities.Languages;
import app.edikodik.com.edikodik.entities.moviesearch.Cinemahalls;
import app.edikodik.com.edikodik.entities.moviesearch.Data;
import app.edikodik.com.edikodik.entities.moviesearch.MovieSearchResponse;
import app.edikodik.com.edikodik.entities.requests.GenericSearchRequest;
import app.edikodik.com.edikodik.enums.ActivityType;
import app.edikodik.com.edikodik.network.NetworkUtility;
import app.edikodik.com.edikodik.services.GenericSearchService;
import app.edikodik.com.edikodik.utilities.AdvertisementUtility;
import app.edikodik.com.edikodik.utilities.CacheDb;
import app.edikodik.com.edikodik.utilities.CommonUtilities;
import app.edikodik.com.edikodik.utilities.LocalUtils;

public class MovieResultActivity extends BaseActivity implements Handler.Callback {

    private ArrayList<Data> baseMovies = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    // Advertisement
    private String targetUrl;

    // Dialog
    Dialog dialog;

    // Genre:
    Spinner spGenres;
    ArrayList<Genres> mDatasetGenre;

    // Language:
    Spinner spLanguage;
    ArrayList<Languages> mDatasetLanguage;

    // Cinemahall
    Spinner spCinemahall;
    ArrayList<Cinemahalls> mDatasetCinemahall;



    static final int DATE_PICKER_ID = 1111;
    Button btnPublishDate;

    // Endless RecyclerView
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 10;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    int currentPage = 1;
    boolean firstEndless = true;
    private boolean endless = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_result);
        super.initializeView(ActivityType.MOVIE_RESULT);
        this.initializeView();
    }


    private void initializeView() {
        Intent intent = getIntent();
        String searchTerm = intent.getStringExtra("searchTerm");


        if (searchTerm == null || searchTerm.isEmpty()) {
            getSupportActionBar().setTitle("Movies");
        } else {
            getSupportActionBar().setTitle(searchTerm);
        }

        // Get data
//        addFeaturedMoviesToBase();
        addMoviesToBase();

        // RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        // specify an adapter (see also next example)
        mAdapter = new MovieSearchResultAdapter(getApplicationContext(), baseMovies, new OnMovieSearchResultClickListener() {
            @Override
            public void onImageClick(Data movie) {
                goToMovieDetailScreen(movie);

            }

            @Override
            public void onBuyOnlineClick(Data movie) {
                actionBuyOnline(movie);
            }

            @Override
            public void onRatingClick(Data movie) {

            }
        });



        // Endless

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = mRecyclerView.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached

                    Log.d("wid", "end called");

                    if (firstEndless == true) {
                        firstEndless = false;
                        return;
                    }
                    endless = true;
                    currentPage++;
                    search();

                    // Do something

                    loading = true;
                }
            }
        });


        mRecyclerView.setAdapter(mAdapter);

        ///////////////////////
        // Dialogbox starts
        //////////////////////
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.search_box_movie);
        dialog.setCancelable(true);
        dialog.setTitle("Search Movie");





        setGenreSpinner();
        setLanguageSpinner();
        setCinemahallSpinner();

        // Date Picker
        setDatePicker();


        Button btnSearch = (Button) dialog.findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Endless
                currentPage = 1;
                endless = false;
                search();
            }
        });


        AdvertisementUtility advertisementUtility = new AdvertisementUtility(this);

        if (CacheDb.getMovieSearchResponse().getAds_mobile() != null) {
            targetUrl = advertisementUtility.setAdvertisement(CacheDb.getMovieSearchResponse().getAds_mobile());
        } else {
            advertisementUtility.setAdvertisement(null);
        }

    }


    private void search() {
        if (!NetworkUtility.isNetworkAvailable(this)) {
            CommonUtilities.toast(this, "No internet connection");
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            return;
        }

        GenericSearchRequest request = new GenericSearchRequest();


        if (CacheDb.selectedMovieGenre != null && !CacheDb.selectedMovieGenre.isEmpty()) {
            request.setGenre(CacheDb.selectedMovieGenre);
        }

        if (!LocalUtils.emptyString(CacheDb.selectedMovieLanguage)) {
            request.setLanguage(CacheDb.selectedMovieLanguage);
        }

        if (CacheDb.selectedMovieCinemahall != null && !CacheDb.selectedMovieCinemahall.isEmpty()) {
            request.setCinemahall(CacheDb.selectedMovieCinemahall);
        }

        if (CacheDb.publishDate != null && !CacheDb.publishDate.isEmpty()) {
            request.setPublishDate(CacheDb.publishDate);
        }

        request.setSearchType("Movie");

        // Endless
        request.setPageNumber(currentPage);
        boolean showProgressBar = false;
        if (currentPage == 1) {
            showProgressBar = true;
        }

        GenericSearchService service = new GenericSearchService(MovieResultActivity.this, this, showProgressBar);
        service.execute(request);
    }


    private void setDatePicker() {
        final Calendar c = Calendar.getInstance();
        CacheDb.year  = c.get(Calendar.YEAR);
        CacheDb.month = c.get(Calendar.MONTH);
        CacheDb.day   = c.get(Calendar.DAY_OF_MONTH);

        btnPublishDate = (Button) dialog.findViewById(R.id.btnPublishDate);

        btnPublishDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // On button click show datepicker dialog
                showDialog(DATE_PICKER_ID);

            }

        });
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:

                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener, CacheDb.year, CacheDb.month,CacheDb.day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            CacheDb.year  = selectedYear;
            CacheDb.month = selectedMonth;
            CacheDb.day   = selectedDay;

            // Show selected date
            CacheDb.publishDate = new StringBuilder().append(CacheDb.day)
                    .append("-").append(CacheDb.month + 1).append("-").append(CacheDb.year)
                    .append(" ").toString();

            btnPublishDate.setText(CacheDb.publishDate);


        }
    };


    private void setGenreSpinner() {
        List<Genres> genres = Genres.listAll(Genres.class);
        mDatasetGenre = new ArrayList<>(genres.size());
        mDatasetGenre.addAll(genres);

        Genres genre = new Genres();
        genre.setGenreId("");
        genre.setGenreName(" -- Select Genre -- ");
        mDatasetGenre.add(0, genre);

        spGenres = (Spinner) dialog.findViewById(R.id.spGenre);
        ArrayAdapter<Genres> dataAdapter = new ArrayAdapter<Genres>(this,
                android.R.layout.simple_spinner_item, mDatasetGenre);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGenres.setAdapter(dataAdapter);



        spGenres.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CacheDb.selectedMovieGenre = mDatasetGenre.get(i).getGenreId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        LocalUtils.setGenreSpinnerValue(spGenres, mDatasetGenre, CacheDb.selectedMovieGenre);
    }



    private void setLanguageSpinner() {
        List<Languages> languages = Languages.listAll(Languages.class);
        mDatasetLanguage = new ArrayList<>(languages.size());
        mDatasetLanguage.addAll(languages);

        Languages language = new Languages();
        language.setId(null);
        language.setName(" -- Select Language -- ");
        mDatasetLanguage.add(0, language);

        spLanguage = (Spinner) dialog.findViewById(R.id.spLanguage);
        ArrayAdapter<Languages> dataAdapter = new ArrayAdapter<Languages>(this,
                android.R.layout.simple_spinner_item, mDatasetLanguage);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLanguage.setAdapter(dataAdapter);



        spLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CacheDb.selectedMovieLanguage = "" + mDatasetLanguage.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        LocalUtils.setLanguageSpinnerValue(spLanguage, mDatasetLanguage, CacheDb.selectedMovieLanguage);
    }


    private void setCinemahallSpinner() {
        List<Cinemahalls> cinemahalls = Cinemahalls.listAll(Cinemahalls.class);
        mDatasetCinemahall = new ArrayList<>(cinemahalls.size());
        mDatasetCinemahall.addAll(cinemahalls);

        Cinemahalls cinemahall = new Cinemahalls();
        cinemahall.setId(null);
        cinemahall.setLabel(" -- Select Cinemahall -- ");
        mDatasetCinemahall.add(0, cinemahall);

        spCinemahall = (Spinner) dialog.findViewById(R.id.spCinemahall);
        ArrayAdapter<Cinemahalls> dataAdapter = new ArrayAdapter<Cinemahalls>(this,
                android.R.layout.simple_spinner_item, mDatasetCinemahall);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCinemahall.setAdapter(dataAdapter);



        spCinemahall.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CacheDb.selectedMovieCinemahall = "" + mDatasetCinemahall.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        LocalUtils.setCinemahallSpinnerValue(spCinemahall, mDatasetCinemahall, CacheDb.selectedMovieCinemahall);

    }

    private void goToMovieDetailScreen(Data movie) {
        CacheDb.setMovieOfSearchResult(movie);
        Intent intent = new Intent(MovieResultActivity.this, MovieDetailActivity.class);
        startActivity(intent);
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

    public void actionBuyOnline(Data movie) {
        String url = movie.getBuyOnlineLink();

        if (url == null || url.isEmpty()) {
            CommonUtilities.toast(this, "URL is empty");
            return;
        }

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }


    private void addFeaturedMoviesToBase() {
        Data[] movies = CacheDb.getMovieSearchResponse().getFeatured().getData();
        for (Data movie : movies) {
            baseMovies.add(movie);
        }
    }

    private void addMoviesToBase() {
        // Get movies from CacheDb
        MovieSearchResponse response = CacheDb.getMovieSearchResponse();

        if (response == null) {
            CommonUtilities.toast(this, "No movie found");
            endless = false;
            return;
        }

        Data[] movies = response.getData();
        for (Data movie : movies) {
            baseMovies.add(movie);
        }
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

    @Override
    public boolean handleMessage(Message message) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        // Endless
        if (endless) {
            loadData();
            return false;
        }


        LocalUtils.activityRouter(this, message, null, null);
        return false;
    }

    private void loadData() {
        addMoviesToBase();
        mAdapter.notifyDataSetChanged(); // TODO: Possibly this line will go to addMoviesToBase() method
    }
}

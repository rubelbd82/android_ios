package app.edikodik.com.edikodik.activities;

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
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import app.edikodik.com.edikodik.R;
import app.edikodik.com.edikodik.adapters.BookSearchResultAdapter;
import app.edikodik.com.edikodik.callbacks.OnBookSearchResultClickListener;
import app.edikodik.com.edikodik.entities.Genres;
import app.edikodik.com.edikodik.entities.Languages;
import app.edikodik.com.edikodik.entities.booksearch.BookSearchResponse;
import app.edikodik.com.edikodik.entities.booksearch.Data;
import app.edikodik.com.edikodik.entities.booksearch.Publishers;
import app.edikodik.com.edikodik.entities.booksearch.Writers;
import app.edikodik.com.edikodik.entities.requests.GenericSearchRequest;
import app.edikodik.com.edikodik.enums.ActivityType;
import app.edikodik.com.edikodik.network.NetworkUtility;
import app.edikodik.com.edikodik.services.GenericSearchService;
import app.edikodik.com.edikodik.utilities.AdvertisementUtility;
import app.edikodik.com.edikodik.utilities.CacheDb;
import app.edikodik.com.edikodik.utilities.CommonUtilities;
import app.edikodik.com.edikodik.utilities.LocalUtils;

public class BookResultActivity extends BaseActivity implements Handler.Callback {

    private ArrayList<Data> baseBooks = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;


    // Dialog
    Dialog dialog;

    // Genre:
    Spinner spGenres;
    ArrayList<Genres> mDatasetGenre;

    // Language:
    Spinner spLanguage;
    ArrayList<Languages> mDatasetLanguage;

    // Publisher
    Spinner spPublisher;
    ArrayList<Publishers> mDatasetPublisher;

    // Writer
    Spinner spWriter;
    ArrayList<Writers> mDatasetWriter;

    // Endless RecyclerView
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 10;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    int currentPage = 1;
    boolean firstEndless = true;
    private boolean endless = false;

    private String targetUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_result);

        super.initializeView(ActivityType.BOOK_RESULT);

        Intent intent = getIntent();
        String searchTerm = intent.getStringExtra("searchTerm");


        if (searchTerm.isEmpty()) {
            getSupportActionBar().setTitle("Books");
        } else {
            getSupportActionBar().setTitle(searchTerm);
        }

        addBooksToBase();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Get movies from CacheDb;



        // specify an adapter (see also next example)
        mAdapter = new BookSearchResultAdapter(getApplicationContext(), baseBooks, new OnBookSearchResultClickListener() {
            @Override
            public void onImageClick(Data book) {
                goToBookDetailScreen(book);
            }

            @Override
            public void onBuyOnlineClick(Data book) {
                actionBuyOnline(book);
            }

            @Override
            public void onRatingClick(Data book) {

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
        dialog.setContentView(R.layout.search_box_book);
        dialog.setCancelable(true);
        dialog.setTitle("Search Movie");

        setGenreSpinner();
        setLanguageSpinner();
        setPublisherSpinner();
        setWriterSpinner();



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

        // Advertisement

        AdvertisementUtility advertisementUtility = new AdvertisementUtility(this);

        if (CacheDb.getCategoryResponse().getAds_mobile() != null) {
            targetUrl = advertisementUtility.setAdvertisement(CacheDb.getCategoryResponse().getAds_mobile());
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


        if (!LocalUtils.emptyString(CacheDb.selectedBookGenre)) {
            request.setGenre(CacheDb.selectedBookGenre);
        }

        if (!LocalUtils.emptyString(CacheDb.selectedBookLanguage)) {
            request.setLanguage(CacheDb.selectedBookLanguage);
        }

        if (!LocalUtils.emptyString(CacheDb.selectedBookPublisher)) {
            request.setPublisher(CacheDb.selectedBookPublisher);
        }

        if (!LocalUtils.emptyString(CacheDb.selectedBookWriter)) {
            request.setWriter(CacheDb.selectedBookWriter);
        }

        request.setSearchType("Book");

        // Endless
        request.setPageNumber(currentPage);
        boolean showProgressBar = false;
        if (currentPage == 1) {
            showProgressBar = true;
        }


        GenericSearchService service = new GenericSearchService(BookResultActivity.this, this, showProgressBar);
        service.execute(request);
    }

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
                CacheDb.selectedBookGenre = mDatasetGenre.get(i).getGenreId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        LocalUtils.setGenreSpinnerValue(spGenres, mDatasetGenre, CacheDb.selectedBookGenre);
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
                CacheDb.selectedBookLanguage = "" + mDatasetLanguage.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        LocalUtils.setLanguageSpinnerValue(spLanguage, mDatasetLanguage, CacheDb.selectedBookLanguage);
    }


    private void setPublisherSpinner() {
        List<Publishers> publishers = Publishers.listAll(Publishers.class);
        mDatasetPublisher = new ArrayList<>(publishers.size());
        mDatasetPublisher.addAll(publishers);

        Publishers publisher = new Publishers();
        publisher.setId(null);
        publisher.setLabel(" -- Select Publisher -- ");
        mDatasetPublisher.add(0, publisher);

        spPublisher = (Spinner) dialog.findViewById(R.id.spPublisher);
        ArrayAdapter<Publishers> dataAdapter = new ArrayAdapter<Publishers>(this,
                android.R.layout.simple_spinner_item, mDatasetPublisher);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPublisher.setAdapter(dataAdapter);

        spPublisher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CacheDb.selectedBookPublisher = "" + mDatasetPublisher.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        LocalUtils.setPublisherSpinnerValue(spPublisher, mDatasetPublisher, CacheDb.selectedBookPublisher);
    }


    private void setWriterSpinner() {
        List<Writers> writers = Writers.listAll(Writers.class);
        mDatasetWriter = new ArrayList<>(writers.size());
        mDatasetWriter.addAll(writers);

        Writers writer = new Writers();
        writer.setId(null);
        writer.setWriter(" -- Select Writer -- ");
        mDatasetWriter.add(0, writer);

        spWriter = (Spinner) dialog.findViewById(R.id.spWriter);
        ArrayAdapter<Writers> dataAdapter = new ArrayAdapter<Writers>(this,
                android.R.layout.simple_spinner_item, mDatasetWriter);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spWriter.setAdapter(dataAdapter);



        spWriter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CacheDb.selectedBookWriter = "" + mDatasetWriter.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        LocalUtils.setWriterSpinnerValue(spWriter, mDatasetWriter, CacheDb.selectedBookWriter);
    }

    private void goToBookDetailScreen(Data book) {
        CacheDb.setBookOfSearchResult(book);
        Intent intent = new Intent(BookResultActivity.this, BookDetailActivity.class);
        startActivity(intent);
    }

    private void addBooksToBase() {
        BookSearchResponse response = CacheDb.getBookSearchResponse();
        if (response == null) {
            CommonUtilities.toast(this, "No movie found");
            endless = false;
            return;
        }
        Data[] movies = response.getData();

        for (Data movie : movies) {
            baseBooks.add(movie);
        }
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

    public void actionBuyOnline(Data book) {
        String url = book.getBuyOnlineLink();

        if (url == null || url.isEmpty()) {
            CommonUtilities.toast(this, "URL is empty");
            return;
        }

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
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
        addBooksToBase();
        mAdapter.notifyDataSetChanged(); // TODO: Possibly this line will go to addBooksToBase() method
    }
}

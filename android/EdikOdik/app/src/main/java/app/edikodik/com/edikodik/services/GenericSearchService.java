package app.edikodik.com.edikodik.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.edikodik.com.edikodik.entities.BaseSubcategories;
import app.edikodik.com.edikodik.entities.booksearch.BookSearchResponse;
import app.edikodik.com.edikodik.entities.genericsearch.GenericSearchResponse;
import app.edikodik.com.edikodik.entities.moviesearch.MovieSearchResponse;
import app.edikodik.com.edikodik.entities.requests.GenericSearchRequest;
import app.edikodik.com.edikodik.enums.ServiceType;
import app.edikodik.com.edikodik.network.NetworkUtility;
import app.edikodik.com.edikodik.utilities.CacheDb;
import app.edikodik.com.edikodik.utilities.CommonUtilities;
import app.edikodik.com.edikodik.utilities.Constants;
import app.edikodik.com.edikodik.utilities.LocalUtils;

/**
 * Created by farhadrubel on 5/28/16.
 */

public class GenericSearchService extends AsyncTask<GenericSearchRequest, Void, Object> {


    private Context context;
    private Handler.Callback callback;
    private ProgressDialog progressDialog;
    private boolean showDialog;
    private final int RESULT_PER_PAGE = 10;

    public GenericSearchService(Context context, Handler.Callback callback, boolean showDialog) {
        this.context = context;
        this.callback = callback;
        this.showDialog = showDialog;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (showDialog) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Please Wait");
            progressDialog.setMessage("Getting Listings...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        } else {
            CommonUtilities.toast(context, "Loading...");
        }
    }

    @Override
    protected Object doInBackground(GenericSearchRequest... params) {

        GenericSearchRequest request = params[0];


        List<NameValuePair> nameValuePairs = new ArrayList<>();
        // set based on search type 1 X


        Object response = null;


        String url = null;

        url = Constants.genericSearch + (Math.abs((request.getPageNumber() - 1)) * RESULT_PER_PAGE);


        // set based on search type 2
        Type type = null;

        if (request.getSearchType() == null) {
            request.setSearchType("Generic");
        }

        if (request.getSearchType().equalsIgnoreCase("Book")) {

            if (!LocalUtils.emptyString(request.getSearchType())) {
                nameValuePairs.add(new BasicNameValuePair("SearchType", request.getSearchType()));
            }

            if (!LocalUtils.emptyString(request.getGenre())) {
                nameValuePairs.add(new BasicNameValuePair("Genre", request.getGenre()));
            }

            if (!LocalUtils.emptyString(request.getLanguage())) {
                nameValuePairs.add(new BasicNameValuePair("Language", request.getLanguage()));
            }

            if (!LocalUtils.emptyString(request.getPublisher())) {
                nameValuePairs.add(new BasicNameValuePair("Publisher", request.getPublisher()));
            }

            if (!LocalUtils.emptyString(request.getWriter())) {
                nameValuePairs.add(new BasicNameValuePair("Writer", request.getWriter()));
            }

            if (!LocalUtils.emptyString(request.getCatId())) {
                nameValuePairs.add(new BasicNameValuePair("CatId", request.getCatId()));
            }

        } else if (request.getSearchType().equalsIgnoreCase("Movie")) {

            if (!LocalUtils.emptyString(request.getSearchType())) {
                nameValuePairs.add(new BasicNameValuePair("SearchType", request.getSearchType()));
            }

            if (!LocalUtils.emptyString(request.getGenre())) {
                nameValuePairs.add(new BasicNameValuePair("Genre", request.getGenre()));
            }

            if (!LocalUtils.emptyString(request.getLanguage())) {
                nameValuePairs.add(new BasicNameValuePair("Language", request.getLanguage()));
            }

            if (!LocalUtils.emptyString(request.getCinemahall())) {
                nameValuePairs.add(new BasicNameValuePair("Cinemahall", request.getCinemahall()));
            }

            if (!LocalUtils.emptyString(request.getPublishDate())) {
                nameValuePairs.add(new BasicNameValuePair("PublishDate", request.getPublishDate()));
            }

            if (!LocalUtils.emptyString(request.getCatId())) {
                nameValuePairs.add(new BasicNameValuePair("CatId", request.getCatId()));
            }


//            response = (MovieSearchResponse) NetworkUtility.readUrl(url, nameValuePairs, MovieSearchResponse.class);
        } else {

            if (!LocalUtils.emptyString(request.getSearchterm())) {
                nameValuePairs.add(new BasicNameValuePair("SearchTerm", request.getSearchterm()));
            }

            if (!LocalUtils.emptyString(request.getCatId())) {
                nameValuePairs.add(new BasicNameValuePair("CatId", request.getCatId()));
            }

            // TODO: Decide about district later
            if (request.getDistrict() != null) {
                nameValuePairs.add(new BasicNameValuePair("District", request.getDistrict()));
            }

            if (request.getLatitude() > 0) {
                nameValuePairs.add(new BasicNameValuePair("Latitude", "" + request.getLatitude()));
            }

            if (request.getLongitude() > 0) {
                nameValuePairs.add(new BasicNameValuePair("Longitude", "" + request.getLongitude()));
            }

        }

        String responseString = NetworkUtility.readSecuredUrl(context, url, nameValuePairs);

        if (responseString.contains("BookId")) {
            // This is movie id
            response = (BookSearchResponse) NetworkUtility.processResponse(responseString, BookSearchResponse.class);
        } else if (responseString.contains("MovieId")) {
            // This is movie id
            response = (MovieSearchResponse) NetworkUtility.processResponse(responseString, MovieSearchResponse.class);
        } else {
            response = (GenericSearchResponse) NetworkUtility.processResponse(responseString, GenericSearchResponse.class);
        }

//        response = NetworkUtility.readUrl(url, nameValuePairs, type.getClass());

//        response = (GenericSearchResponse) NetworkUtility.readUrl(url, nameValuePairs, GenericSearchResponse.class);
//        Log.d("wid", "লিস্টিং নাম: " + genericSearchResponse.getListings()[0].getName());

        return response;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        if (showDialog) {
            if (progressDialog != null)
                progressDialog.dismiss();
            progressDialog = null;
        }

        Message message = new Message();

        if (result instanceof GenericSearchResponse) {
            CacheDb.setGenericSearchResponse((GenericSearchResponse) result);
            message.arg1 = ServiceType.GENERIC_SEARCH_LISTING.ordinal();
        } else if (result instanceof MovieSearchResponse) {
            CacheDb.setMovieSearchResponse((MovieSearchResponse) result);
            message.arg1 = ServiceType.GENERIC_SEARCH_MOVIE.ordinal();
        } else if (result instanceof BookSearchResponse) {
            CacheDb.setBookSearchResponse((BookSearchResponse) result);
            message.arg1 = ServiceType.GENERIC_SEARCH_BOOK.ordinal();
        }

        callback.handleMessage(message);
    }




}
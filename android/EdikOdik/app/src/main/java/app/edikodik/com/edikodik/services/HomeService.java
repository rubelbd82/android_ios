package app.edikodik.com.edikodik.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import org.apache.http.NameValuePair;
import java.util.ArrayList;
import java.util.List;

import app.edikodik.com.edikodik.dao.CategoryDao;
import app.edikodik.com.edikodik.dao.CinemahallDao;
import app.edikodik.com.edikodik.dao.DistrictDao;
import app.edikodik.com.edikodik.dao.GenericDao;
import app.edikodik.com.edikodik.dao.GenreDao;
import app.edikodik.com.edikodik.dao.LanguageDao;
import app.edikodik.com.edikodik.dao.PublisherDao;
import app.edikodik.com.edikodik.dao.WriterDao;
import app.edikodik.com.edikodik.entities.Categories;
import app.edikodik.com.edikodik.entities.Districts;
import app.edikodik.com.edikodik.entities.Genres;
import app.edikodik.com.edikodik.entities.HomeResponse;
import app.edikodik.com.edikodik.entities.Language;
import app.edikodik.com.edikodik.entities.Languages;
import app.edikodik.com.edikodik.entities.booksearch.Publishers;
import app.edikodik.com.edikodik.entities.booksearch.Writers;
import app.edikodik.com.edikodik.entities.moviesearch.Cinemahalls;
import app.edikodik.com.edikodik.network.NetworkUtility;
import app.edikodik.com.edikodik.utilities.AppPreferences;
import app.edikodik.com.edikodik.utilities.Constants;

/**
 * Created by farhadrubel on 5/28/16.
 */

public class HomeService extends AsyncTask<Void, Void, HomeResponse> {


    private Context context;
    private Handler.Callback callback;
    private ProgressDialog progressDialog;
    private boolean showDialog;

    public HomeService(Context context, Handler.Callback callback, boolean showDialog) {
        this.context = context;
        this.callback = callback;
        this.showDialog = showDialog;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (showDialog) {
            progressDialog = ProgressDialog.show(context, "Please Wait...", "Synching with server", true);
            progressDialog.setCancelable(false);
        }
    }

    @Override
    protected HomeResponse doInBackground(Void... params) {

        List<NameValuePair> nameValuePairs = new ArrayList<>();
        HomeResponse homeResponse = new HomeResponse();
        homeResponse = (HomeResponse) NetworkUtility.readSecuredUrl(context, Constants.home, nameValuePairs, HomeResponse.class);

         if (homeResponse == null    ) {
            return null;
        }

        try {
            GenericDao genericDaoCategory = new CategoryDao();
            genericDaoCategory.sync(homeResponse.getCategories());
            AppPreferences.setLastUpdate(context, System.currentTimeMillis()/1000);

        } catch (Exception e) {

        }
        List<Districts> districts = new ArrayList<>();

       try {
           districts = Districts.listAll(Districts.class);
           Log.d("wid", "Total districts are: " + districts.size());

           List<Categories> categories = Categories.listAll(Categories.class);
           Log.d("wid", "Total categories are: " + categories.size());
       } catch (Exception e) {
           Log.d("wid", "Exception is: " + e.getMessage());
       }

        try {
            if (districts.size() < 1) {
                GenericDao genericDaoDistrict = new DistrictDao();
                genericDaoDistrict.sync(homeResponse.getDistricts());
            }
        } catch (Exception e) {
            Log.d("wid", "Exception Raised: " + e.getMessage());
        }


        try {

            Languages[] languages = homeResponse.getLanguages();

            if (languages != null && languages.length > 0) {
                GenericDao dao = new LanguageDao();
                dao.sync(languages);
            }

            Publishers[] publishers = homeResponse.getPublishers();

            if (publishers != null && publishers.length > 0) {
                GenericDao dao = new PublisherDao();
                dao.sync(publishers);
            }


            Cinemahalls[] cinemahalls = homeResponse.getCinemahalls();

            if (cinemahalls != null && cinemahalls.length > 0) {
                GenericDao dao = new CinemahallDao();
                dao.sync(cinemahalls);
            }

            Genres[] genres = homeResponse.getGenres();

            if (genres != null && genres.length > 0) {
                GenericDao dao = new GenreDao();
                dao.sync(genres);
            }

            Writers[] writers = homeResponse.getWriters();

            if (writers != null && writers.length > 0) {
                GenericDao dao = new WriterDao();
                dao.sync(writers);
            }

        } catch (Exception e) {
            Log.d("wid", "Exception Raised: " + e.getMessage());
        }

        return homeResponse;
    }

    @Override
    protected void onPostExecute(HomeResponse result) {
        super.onPostExecute(result);
        if (showDialog) {
            progressDialog.dismiss();
        }

        Message message = new Message();
        message.obj = result;
        callback.handleMessage(message);
    }

}
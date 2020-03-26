package app.edikodik.com.edikodik.utilities;

import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.widget.Spinner;

import java.util.ArrayList;

import app.edikodik.com.edikodik.activities.BookResultActivity;
import app.edikodik.com.edikodik.activities.CategoryActivity;
import app.edikodik.com.edikodik.activities.GenericSearchResultActivity;
import app.edikodik.com.edikodik.activities.MovieResultActivity;
import app.edikodik.com.edikodik.entities.Districts;
import app.edikodik.com.edikodik.entities.Genres;
import app.edikodik.com.edikodik.entities.Languages;
import app.edikodik.com.edikodik.entities.booksearch.Publishers;
import app.edikodik.com.edikodik.entities.booksearch.Writers;
import app.edikodik.com.edikodik.entities.moviesearch.Cinemahalls;
import app.edikodik.com.edikodik.enums.ServiceType;

/**
 * Created by farhadrubel on 7/10/16.
 */

public class LocalUtils {

    public static void setGenreSpinnerValue(Spinner spinners, ArrayList<Genres> genres, String value) {
        if (value == null || value.isEmpty()) {
            return;
        }
        for (int i= 0; i< genres.size(); i++) {
            String genre = ""+genres.get(i).getGenreId();
            if (genre.equalsIgnoreCase(value)) {
                spinners.setSelection(i);
                break;
            }
        }
    }

    public static void setLanguageSpinnerValue(Spinner spinner, ArrayList<Languages> languages, String value) {
        if (value == null || value.isEmpty()) {
            return;
        }
        for (int i= 0; i< languages.size(); i++) {
            String language = ""+languages.get(i).getId();
            if (language.equalsIgnoreCase(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    public static void setCinemahallSpinnerValue(Spinner spinner, ArrayList<Cinemahalls> cinemahalls, String value) {
        if (value == null || value.isEmpty()) {
            return;
        }
        for (int i= 0; i< cinemahalls.size(); i++) {
            String cinemahall = ""+cinemahalls.get(i).getId();
            if (cinemahall.equalsIgnoreCase(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    public static void setPublisherSpinnerValue(Spinner spinner, ArrayList<Publishers> publishers, String value) {
        if (value == null || value.isEmpty()) {
            return;
        }
        for (int i= 0; i< publishers.size(); i++) {
            String publisher = ""+publishers.get(i).getId();
            if (publisher.equalsIgnoreCase(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    public static void setWriterSpinnerValue(Spinner spinner, ArrayList<Writers> writers, String value) {
        if (value == null || value.isEmpty()) {
            return;
        }
        for (int i= 0; i< writers.size(); i++) {
            String writer = ""+writers.get(i).getId();
            if (writer.equalsIgnoreCase(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    public static void setDistrictSpinnerValue(Spinner spinner, ArrayList<Districts> districts, String value) {
        if (value == null || value.isEmpty()) {
            return;
        }
        for (int i= 0; i< districts.size(); i++) {
            String district = ""+districts.get(i).getId();
            if (district.equalsIgnoreCase(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }



    // Local Methods

    public static void activityRouter(Activity activity, Message message, String searchTerm, String selectedCategory) {
        if (message.arg1 == ServiceType.CATEGORY_SERVICE.ordinal()) {
            Intent intent = new Intent(activity, CategoryActivity.class);
            intent.putExtra("categoryName", selectedCategory);
            activity.startActivity(intent);
        }

        if (message.arg1 == ServiceType.MOVIE_SEARCH.ordinal() || message.arg1 == ServiceType.GENERIC_SEARCH_MOVIE.ordinal()) {
            Intent intent = new Intent(activity, MovieResultActivity.class);
            intent.putExtra("searchTerm", "Movies");
            activity.startActivity(intent);
        }

        if (message.arg1 == ServiceType.BOOK_SEARCH.ordinal() || message.arg1 == ServiceType.GENERIC_SEARCH_BOOK.ordinal() ) {
            Intent intent = new Intent(activity, BookResultActivity.class);
            intent.putExtra("searchTerm", "Books");
            activity.startActivity(intent);
        }

        if (message.arg1 == ServiceType.GENERIC_SEARCH_LISTING.ordinal()) {
            Intent intent = new Intent(activity, GenericSearchResultActivity.class);
            intent.putExtra("searchTerm", searchTerm);
            activity.startActivity(intent);
        }
    }

    public static boolean emptyString(String string) {
        if (string == null || string.equalsIgnoreCase("") || string.equalsIgnoreCase("null") || string.isEmpty()) {
            return true;
        }

        return false;
    }
}

package app.edikodik.com.edikodik.utilities;

import app.edikodik.com.edikodik.entities.CategoryResponse;

/**
 * Created by farhadrubel on 12/29/14.
 */
public class Constants {
    public static String format = "?format=json&FormSubmitted=true";
    public static String baseUrl = "https://edikodik.com/index.php/";
//    public static String baseUrl = "http://localhost:8888/ci3/index.php";
    public static String serverError = "Server error. Try again later.";
    public static String statusSuccess = "1";
    public static String statusFail = "0";

    // Service names

    public static String home = "front/sync_mobile_data/"; // Category
    public static String socialSigning = "hauth/login_authentication_api/";
    public static String signin = "auth/login_api/";
    public static String signUp = "auth/register_api/";
    public static String category = "front/category/";
    public static String subCategory = "front/search_by_subcategory/";
    public static String genericDetail = "front/generic_details/";
    public static String movieResult = "front/category/36/Movies";
    public static String movieDetail = "front/movie_book_details/";
    public static String bookResult = "front/category/14/Books/";
    public static String bookDetail = "front/book_details/";
    public static String genericSearch = "front/search/";
    public static String cms = "front/cms/";
    public static String freeListing = "front/free_listing/";
    public static String autoComplete = "Listings_ctrl/get_Searchterm_list/";

    // Rating
    public static String ratingGeneric = "utilities/set_rating/";
    public static String ratingMovie = "utilities/set_movie_rating/";
    public static String ratingBook = "utilities/set_book_rating/";


    public static String ABOUT = "10";
    public static String TERMS = "13";
    public static String CONTACT = "14";
    public static String ADVERTISE = "15";
    public static String MEDIA = "11";
    public static String TESTIMONIAL = "12";





}

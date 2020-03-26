package app.edikodik.com.edikodik.utilities;

import app.edikodik.com.edikodik.entities.CategoryResponse;
import app.edikodik.com.edikodik.entities.bookdetail.BookDetailResponse;
import app.edikodik.com.edikodik.entities.booksearch.BookSearchResponse;
import app.edikodik.com.edikodik.entities.cms.CmsResponse;
import app.edikodik.com.edikodik.entities.freelisting.FreeListingResponse;
import app.edikodik.com.edikodik.entities.genericdetail.GenericDetailResponse;
import app.edikodik.com.edikodik.entities.genericsearch.GenericSearchResponse;
import app.edikodik.com.edikodik.entities.genericsearch.Listings;
import app.edikodik.com.edikodik.entities.moviedetail.MovieDetailResponse;
import app.edikodik.com.edikodik.entities.moviesearch.Data;
import app.edikodik.com.edikodik.entities.moviesearch.MovieSearchResponse;

/**
 * Created by farhadrubel on 6/4/16.
 */

public class CacheDb {
    // Temporary Transporter

    private static CategoryResponse categoryResponse;
    private static GenericSearchResponse genericSearchResponse;
    private static Listings genericSearchResultListing;
    private static GenericDetailResponse genericDetailResponse;
    private static MovieSearchResponse movieSearchResponse;
    private static Data movieOfSearchResult;
    private static MovieDetailResponse movieDetailResponse;
    private static BookSearchResponse bookSearchResponse;
    private static BookDetailResponse bookDetailResponse;
    private static CmsResponse cmsResponse;
    private static FreeListingResponse freeListingResponse;


//    Some session variables
    // Generic
    public static String selectedGenericDistrict;
    public static String searchTerm;

    // Movie
    public static String selectedMovieGenre = null;
    public static String selectedMovieLanguage = null;
    public static String selectedMovieCinemahall =  null;
    public static String publishDate;
    // Date Picker
    public static  int year;
    public static  int month;
    public static  int day;

    // Book
    public static String selectedBookGenre;
    public static String selectedBookLanguage;
    public static String selectedBookPublisher;
    public static String selectedBookWriter;



    private static app.edikodik.com.edikodik.entities.booksearch.Data bookOfSearchResult;

    public static CategoryResponse getCategoryResponse() {
        return categoryResponse;
    }

    public static void setCategoryResponse(CategoryResponse categoryResponse) {
        CacheDb.categoryResponse = null;
        CacheDb.categoryResponse = categoryResponse;
    }


    public static GenericSearchResponse getGenericSearchResponse() {
        return genericSearchResponse;
    }

    public static void setGenericSearchResponse(GenericSearchResponse genericSearchResponse) {
        CacheDb.genericSearchResponse = genericSearchResponse;
    }

    public static Listings getGenericSearchResultListing() {
        return genericSearchResultListing;
    }

    public static void setGenericSearchResultListing(Listings genericSearchResultListing) {
        CacheDb.genericSearchResultListing = genericSearchResultListing;
    }

    public static GenericDetailResponse getGenericDetailResponse() {
        return genericDetailResponse;
    }

    public static void setGenericDetailResponse(GenericDetailResponse genericDetailResponse) {
        CacheDb.genericDetailResponse = genericDetailResponse;
    }

    public static MovieSearchResponse getMovieSearchResponse() {
        return movieSearchResponse;
    }

    public static void setMovieSearchResponse(MovieSearchResponse movieSearchResponse) {
        CacheDb.movieSearchResponse = movieSearchResponse;
    }

    public static BookSearchResponse getBookSearchResponse() {
        return bookSearchResponse;
    }

    public static void setBookSearchResponse(BookSearchResponse bookSearchResponse) {
        CacheDb.bookSearchResponse = bookSearchResponse;
    }

    public static Data getMovieOfSearchResult() {
        return movieOfSearchResult;
    }

    public static void setMovieOfSearchResult(Data movieOfSearchResult) {
        CacheDb.movieOfSearchResult = movieOfSearchResult;
    }

    public static MovieDetailResponse getMovieDetailResponse() {
        return movieDetailResponse;
    }

    public static void setMovieDetailResponse(MovieDetailResponse movieDetailResponse) {
        CacheDb.movieDetailResponse = movieDetailResponse;
    }

    public static app.edikodik.com.edikodik.entities.booksearch.Data getBookOfSearchResult() {
        return bookOfSearchResult;
    }

    public static void setBookOfSearchResult(app.edikodik.com.edikodik.entities.booksearch.Data bookOfSearchResult) {
        CacheDb.bookOfSearchResult = bookOfSearchResult;
    }

    public static BookDetailResponse getBookDetailResponse() {
        return bookDetailResponse;
    }

    public static void setBookDetailResponse(BookDetailResponse bookDetailResponse) {
        CacheDb.bookDetailResponse = bookDetailResponse;
    }

    public static CmsResponse getCmsResponse() {
        return cmsResponse;
    }

    public static void setCmsResponse(CmsResponse cmsResponse) {
        CacheDb.cmsResponse = cmsResponse;
    }

    public static FreeListingResponse getFreeListingResponse() {
        return freeListingResponse;
    }

    public static void setFreeListingResponse(FreeListingResponse freeListingResponse) {
        CacheDb.freeListingResponse = freeListingResponse;
    }
}



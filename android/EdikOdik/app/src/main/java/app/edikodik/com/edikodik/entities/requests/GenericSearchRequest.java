package app.edikodik.com.edikodik.entities.requests;

/**
 * Created by farhadrubel on 6/19/16.
 */

public class GenericSearchRequest {
    String searchterm, district;
    double latitude, longitude;

    String searchType;

    // Book
    String publisher, genre, writer, language;

    // Movie
    String cinemahall, publishDate;

    String catId;

    int pageNumber;

    public String getSearchterm() {
        return searchterm;
    }

    public void setSearchterm(String searchterm) {
        this.searchterm = searchterm;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCinemahall() {
        return cinemahall;
    }

    public void setCinemahall(String cinemahall) {
        this.cinemahall = cinemahall;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }
}

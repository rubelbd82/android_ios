package app.edikodik.com.edikodik.entities.moviedetail;

import app.edikodik.com.edikodik.entities.Genres;
import app.edikodik.com.edikodik.entities.Language;
import app.edikodik.com.edikodik.entities.Languages;
import app.edikodik.com.edikodik.entities.moviesearch.Cinemahalls;

/**
 * Created by farhadrubel on 6/12/16.
 */

public class MovieDetailResponse
{
    private String genre;

    private String total;

    private Languages[] languages;

    private String logo;

    private Genres[] genres;

    private Cinemahalls[] cinemahalls;

    private String thumbnail_image;

    private Movie movie;

    private String ads_top;

    private String ads_mobile;

    private Params params;

    private Language language;

    private Listings[] listings;

    private String user;

    public String getGenre ()
    {
        return genre;
    }

    public void setGenre (String genre)
    {
        this.genre = genre;
    }

    public String getTotal ()
    {
        return total;
    }

    public void setTotal (String total)
    {
        this.total = total;
    }

    public Languages[] getLanguages ()
    {
        return languages;
    }

    public void setLanguages (Languages[] languages)
    {
        this.languages = languages;
    }

    public String getLogo ()
    {
        return logo;
    }

    public void setLogo (String logo)
    {
        this.logo = logo;
    }

    public Genres[] getGenres ()
    {
        return genres;
    }

    public void setGenres (Genres[] genres)
    {
        this.genres = genres;
    }

    public Cinemahalls[] getCinemahalls ()
    {
        return cinemahalls;
    }

    public void setCinemahalls (Cinemahalls[] cinemahalls)
    {
        this.cinemahalls = cinemahalls;
    }

    public String getThumbnail_image ()
    {
        return thumbnail_image;
    }

    public void setThumbnail_image (String thumbnail_image)
    {
        this.thumbnail_image = thumbnail_image;
    }

    public Movie getMovie ()
    {
        return movie;
    }

    public void setMovie (Movie movie)
    {
        this.movie = movie;
    }

    public String getAds_top ()
    {
        return ads_top;
    }

    public void setAds_top (String ads_top)
    {
        this.ads_top = ads_top;
    }

    public Params getParams ()
    {
        return params;
    }

    public void setParams (Params params)
    {
        this.params = params;
    }

    public Language getLanguage ()
    {
        return language;
    }

    public void setLanguage (Language language)
    {
        this.language = language;
    }

    public Listings[] getListings ()
    {
        return listings;
    }

    public void setListings (Listings[] listings)
    {
        this.listings = listings;
    }

    public String getUser ()
{
    return user;
}

    public void setUser (String user)
    {
        this.user = user;
    }

    public String getAds_mobile() {
        return ads_mobile;
    }

    public void setAds_mobile(String ads_mobile) {
        this.ads_mobile = ads_mobile;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [genre = "+genre+", total = "+total+", languages = "+languages+", logo = "+logo+", genres = "+genres+", cinemahalls = "+cinemahalls+", thumbnail_image = "+thumbnail_image+", movie = "+movie+", ads_top = "+ads_top+", params = "+params+", language = "+language+", listings = "+listings+", user = "+user+"]";
    }
}

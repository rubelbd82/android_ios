package app.edikodik.com.edikodik.entities.bookdetail;

import app.edikodik.com.edikodik.entities.Genres;
import app.edikodik.com.edikodik.entities.Languages;
import app.edikodik.com.edikodik.entities.Language;
import app.edikodik.com.edikodik.entities.booksearch.Writers;

/**
 * Created by farhadrubel on 6/12/16.
 */

public class BookDetailResponse
{
    private String total;

    private String genre;

    private String logo;

    private Publishers[] publishers;

    private Genres[] genres;

    private Movie movie;

    private Params params;

    private Listings[] listings;

    private Languages[] languages;

    private String thumbnail_image;

    private String ads_top;
    private String ads_mobile;

    private Writers[] writers;

    private Language language;

    private String user;

    public String getTotal ()
    {
        return total;
    }

    public void setTotal (String total)
    {
        this.total = total;
    }

    public String getGenre ()
    {
        return genre;
    }

    public void setGenre (String genre)
    {
        this.genre = genre;
    }

    public String getLogo ()
    {
        return logo;
    }

    public void setLogo (String logo)
    {
        this.logo = logo;
    }

    public Publishers[] getPublishers ()
    {
        return publishers;
    }

    public void setPublishers (Publishers[] publishers)
    {
        this.publishers = publishers;
    }

    public Genres[] getGenres ()
    {
        return genres;
    }

    public void setGenres (Genres[] genres)
    {
        this.genres = genres;
    }

    public Movie getMovie ()
    {
        return movie;
    }

    public void setMovie (Movie movie)
    {
        this.movie = movie;
    }

    public Params getParams ()
    {
        return params;
    }

    public void setParams (Params params)
    {
        this.params = params;
    }

    public Listings[] getListings ()
    {
        return listings;
    }

    public void setListings (Listings[] listings)
    {
        this.listings = listings;
    }

    public Languages[] getLanguages ()
    {
        return languages;
    }

    public void setLanguages (Languages[] languages)
    {
        this.languages = languages;
    }

    public String getThumbnail_image ()
    {
        return thumbnail_image;
    }

    public void setThumbnail_image (String thumbnail_image)
    {
        this.thumbnail_image = thumbnail_image;
    }

    public String getAds_top ()
    {
        return ads_top;
    }

    public void setAds_top (String ads_top)
    {
        this.ads_top = ads_top;
    }

    public Writers[] getWriters ()
    {
        return writers;
    }

    public void setWriters (Writers[] writers)
    {
        this.writers = writers;
    }

    public Language getLanguage ()
    {
        return language;
    }

    public void setLanguage (Language language)
    {
        this.language = language;
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
        return "ClassPojo [total = "+total+", genre = "+genre+", logo = "+logo+", publishers = "+publishers+", genres = "+genres+", movie = "+movie+", params = "+params+", listings = "+listings+", languages = "+languages+", thumbnail_image = "+thumbnail_image+", ads_top = "+ads_top+", writers = "+writers+", language = "+language+", user = "+user+"]";
    }
}
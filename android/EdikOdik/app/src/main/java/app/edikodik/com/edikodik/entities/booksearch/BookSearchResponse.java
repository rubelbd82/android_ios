package app.edikodik.com.edikodik.entities.booksearch;

import app.edikodik.com.edikodik.entities.Genres;
import app.edikodik.com.edikodik.entities.Languages;

/**
 * Created by farhadrubel on 6/12/16.
 */

public class BookSearchResponse
{
    private String total;

    private Languages[] languages;

    private String logo;

    private Publishers[] publishers;

    private Genres[] genres;

    private Data[] data;

    private Writers[] writers;

    private String ads_top;

    private String ads_mobile;

    private Params params;

    private String pagination;

    private String user;

    private Featured Featured;

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

    public Data[] getData ()
    {
        return data;
    }

    public void setData (Data[] data)
    {
        this.data = data;
    }

    public Writers[] getWriters ()
    {
        return writers;
    }

    public void setWriters (Writers[] writers)
    {
        this.writers = writers;
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

    public String getPagination ()
    {
        return pagination;
    }

    public void setPagination (String pagination)
    {
        this.pagination = pagination;
    }

    public String getUser ()
{
    return user;
}

    public void setUser (String user)
    {
        this.user = user;
    }

    public Featured getFeatured ()
    {
        return Featured;
    }

    public void setFeatured (Featured Featured)
    {
        this.Featured = Featured;
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
        return "ClassPojo [total = "+total+", languages = "+languages+", logo = "+logo+", publishers = "+publishers+", genres = "+genres+", data = "+data+", writers = "+writers+", ads_top = "+ads_top+", params = "+params+", pagination = "+pagination+", user = "+user+", Featured = "+Featured+"]";
    }
}
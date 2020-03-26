package app.edikodik.com.edikodik.entities;

import com.orm.SugarRecord;

import app.edikodik.com.edikodik.entities.booksearch.Publishers;
import app.edikodik.com.edikodik.entities.booksearch.Writers;
import app.edikodik.com.edikodik.entities.moviesearch.Cinemahalls;
import app.edikodik.com.edikodik.entities.moviesearch.Params;

/**
 * Created by farhadrubel on 5/23/16.
 */
public class HomeResponse
{
    private Languages[] languages;

    private Publishers[] publishers;

    private Cinemahalls[] cinemahalls;

    private Genres[] genres;

    private Districts[] districts;

    private Writers[] writers;

    private Categories[] categories;

    private String user;

    public Languages[] getLanguages ()
    {
        return languages;
    }

    public void setLanguages (Languages[] languages)
    {
        this.languages = languages;
    }

    public Publishers[] getPublishers ()
    {
        return publishers;
    }

    public void setPublishers (Publishers[] publishers)
    {
        this.publishers = publishers;
    }

    public Cinemahalls[] getCinemahalls ()
    {
        return cinemahalls;
    }

    public void setCinemahalls (Cinemahalls[] cinemahalls)
    {
        this.cinemahalls = cinemahalls;
    }

    public Genres[] getGenres ()
    {
        return genres;
    }

    public void setGenres (Genres[] genres)
    {
        this.genres = genres;
    }

    public Districts[] getDistricts ()
    {
        return districts;
    }

    public void setDistricts (Districts[] districts)
    {
        this.districts = districts;
    }

    public Writers[] getWriters ()
    {
        return writers;
    }

    public void setWriters (Writers[] writers)
    {
        this.writers = writers;
    }

    public Categories[] getCategories ()
    {
        return categories;
    }

    public void setCategories (Categories[] categories)
    {
        this.categories = categories;
    }

    public String getUser ()
{
    return user;
}

    public void setUser (String user)
    {
        this.user = user;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [languages = "+languages+", publishers = "+publishers+", cinemahalls = "+cinemahalls+", genres = "+genres+", districts = "+districts+", writers = "+writers+", categories = "+categories+", user = "+user+"]";
    }
}
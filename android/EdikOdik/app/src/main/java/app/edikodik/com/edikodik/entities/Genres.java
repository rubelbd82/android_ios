package app.edikodik.com.edikodik.entities;

import com.orm.SugarRecord;

/**
 * Created by farhadrubel on 6/5/16.
 */

public class Genres extends SugarRecord
{
    private String Description;

    private String GenreId;

    private String GenreName;

    public String getDescription ()
    {
        return Description;
    }

    public void setDescription (String Description)
    {
        this.Description = Description;
    }

    public String getGenreId ()
    {
        return GenreId;
    }

    public void setGenreId (String GenreId)
    {
        this.GenreId = GenreId;
    }

    public String getGenreName ()
    {
        return GenreName;
    }

    public void setGenreName (String GenreName)
    {
        this.GenreName = GenreName;
    }

    @Override
    public String toString()
    {
        return GenreName;
    }
}

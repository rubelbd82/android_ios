package app.edikodik.com.edikodik.entities.bookdetail;

/**
 * Created by farhadrubel on 6/12/16.
 */

public class Publishers
{
    private String id;

    private String label;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getLabel ()
    {
        return label;
    }

    public void setLabel (String label)
    {
        this.label = label;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", label = "+label+"]";
    }
}

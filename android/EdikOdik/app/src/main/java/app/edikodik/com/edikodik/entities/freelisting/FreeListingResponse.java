package app.edikodik.com.edikodik.entities.freelisting;

/**
 * Created by farhadrubel on 7/9/16.
 */

public class FreeListingResponse
{
    private String message;

    private String status;

    private String user;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
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
        return "ClassPojo [message = "+message+", status = "+status+", user = "+user+"]";
    }
}
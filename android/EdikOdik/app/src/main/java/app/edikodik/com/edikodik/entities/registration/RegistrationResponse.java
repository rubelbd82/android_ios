package app.edikodik.com.edikodik.entities.registration;

/**
 * Created by farhadrubel on 6/15/16.
 */

public class RegistrationResponse {
    private String message;

    private String status;

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

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", status = "+status+"]";
    }
}

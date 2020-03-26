package app.edikodik.com.edikodik.entities.login;

/**
 * Created by farhadrubel on 6/15/16.
 */

public class LoginResponse
{
    private User_info user_info;

    private String message;

    private String session_id;

    private String status;

    private User user;

    public User_info getUser_info ()
    {
        return user_info;
    }

    public void setUser_info (User_info user_info)
    {
        this.user_info = user_info;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getSession_id ()
    {
        return session_id;
    }

    public void setSession_id (String session_id)
    {
        this.session_id = session_id;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public User getUser ()
    {
        return user;
    }

    public void setUser (User user)
    {
        this.user = user;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [user_info = "+user_info+", message = "+message+", session_id = "+session_id+", status = "+status+", user = "+user+"]";
    }
}
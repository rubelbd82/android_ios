package app.edikodik.com.edikodik.entities.cms;

/**
 * Created by farhadrubel on 7/4/16.
 */

public class CmsResponse
{
    private String logo;

    private Menu[] menu;

    private Title_content[] title_content;

    private String user;

    public String getLogo ()
    {
        return logo;
    }

    public void setLogo (String logo)
    {
        this.logo = logo;
    }

    public Menu[] getMenu ()
    {
        return menu;
    }

    public void setMenu (Menu[] menu)
    {
        this.menu = menu;
    }

    public Title_content[] getTitle_content ()
    {
        return title_content;
    }

    public void setTitle_content (Title_content[] title_content)
    {
        this.title_content = title_content;
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
        return "ClassPojo [logo = "+logo+", menu = "+menu+", title_content = "+title_content+", user = "+user+"]";
    }
}
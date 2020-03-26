package app.edikodik.com.edikodik.entities.cms;

/**
 * Created by farhadrubel on 7/8/16.
 */

public class Title_content
{
    private String Published;

    private String CmsId;

    private String Content;

    private String Title;

    public String getPublished ()
    {
        return Published;
    }

    public void setPublished (String Published)
    {
        this.Published = Published;
    }

    public String getCmsId ()
    {
        return CmsId;
    }

    public void setCmsId (String CmsId)
    {
        this.CmsId = CmsId;
    }

    public String getContent ()
    {
        return Content;
    }

    public void setContent (String Content)
    {
        this.Content = Content;
    }

    public String getTitle ()
    {
        return Title;
    }

    public void setTitle (String Title)
    {
        this.Title = Title;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Published = "+Published+", CmsId = "+CmsId+", Content = "+Content+", Title = "+Title+"]";
    }
}

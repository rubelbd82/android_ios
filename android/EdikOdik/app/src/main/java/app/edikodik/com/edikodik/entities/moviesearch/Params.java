package app.edikodik.com.edikodik.entities.moviesearch;

/**
 * Created by farhadrubel on 5/23/16.
 */
public class Params {
    private String DX_username;

    private String DX_logged_in;

    private String[] DX_parent_roles_id;

    private String[] DX_parent_roles_name;

    private String[] DX_parent_permissions;

    private String DX_role_id;

    private String[] DX_permission;

    private String DX_role_name;

    private String DX_user_id;

    public String getDX_username ()
    {
        return DX_username;
    }

    public void setDX_username (String DX_username)
    {
        this.DX_username = DX_username;
    }

    public String getDX_logged_in ()
    {
        return DX_logged_in;
    }

    public void setDX_logged_in (String DX_logged_in)
    {
        this.DX_logged_in = DX_logged_in;
    }

    public String[] getDX_parent_roles_id ()
    {
        return DX_parent_roles_id;
    }

    public void setDX_parent_roles_id (String[] DX_parent_roles_id)
    {
        this.DX_parent_roles_id = DX_parent_roles_id;
    }

    public String[] getDX_parent_roles_name ()
    {
        return DX_parent_roles_name;
    }

    public void setDX_parent_roles_name (String[] DX_parent_roles_name)
    {
        this.DX_parent_roles_name = DX_parent_roles_name;
    }

    public String[] getDX_parent_permissions ()
    {
        return DX_parent_permissions;
    }

    public void setDX_parent_permissions (String[] DX_parent_permissions)
    {
        this.DX_parent_permissions = DX_parent_permissions;
    }

    public String getDX_role_id ()
    {
        return DX_role_id;
    }

    public void setDX_role_id (String DX_role_id)
    {
        this.DX_role_id = DX_role_id;
    }

    public String[] getDX_permission ()
    {
        return DX_permission;
    }

    public void setDX_permission (String[] DX_permission)
    {
        this.DX_permission = DX_permission;
    }

    public String getDX_role_name ()
    {
        return DX_role_name;
    }

    public void setDX_role_name (String DX_role_name)
    {
        this.DX_role_name = DX_role_name;
    }

    public String getDX_user_id ()
    {
        return DX_user_id;
    }

    public void setDX_user_id (String DX_user_id)
    {
        this.DX_user_id = DX_user_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [DX_username = "+DX_username+", DX_logged_in = "+DX_logged_in+", DX_parent_roles_id = "+DX_parent_roles_id+", DX_parent_roles_name = "+DX_parent_roles_name+", DX_parent_permissions = "+DX_parent_permissions+", DX_role_id = "+DX_role_id+", DX_permission = "+DX_permission+", DX_role_name = "+DX_role_name+", DX_user_id = "+DX_user_id+"]";
    }
}

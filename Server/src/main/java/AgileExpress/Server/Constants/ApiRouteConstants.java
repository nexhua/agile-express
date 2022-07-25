package AgileExpress.Server.Constants;

public class ApiRouteConstants {

    //region Auth Routes

    public static final String SignUp = "/api/auth/signup";

    //endregion

    //region User Routes

    public static final String GetUsers = "/api/users";

    public static final String GetUser = "/api/users/{username}";

    //endregion

    //region Project Routes

    public static final String GetProject = "/api/projects/{projectID}";
    public static final String GetProjects = "/api/projects";
    public static final String AddProject = "/api/projects/new";
    public static final String ProjectAddUser = "/api/projects/add";

    //endregion
}

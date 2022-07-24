package AgileExpress.Server.Constants;

public class ApiRouteConstants {

    //region Auth Routes

    public static final String SignUp = "/api/auth/signup";

    //endregion

    //region User Routes

    public static final String GetUsers = "api/users";

    public static final String GetUser = "api/users/{username}";

    //endregion

    //region Project Routes

    public static final String AddProject = "api/project/new";

    public static final String ProjectAddUser = "api/project/{username}";

    //endregion
}

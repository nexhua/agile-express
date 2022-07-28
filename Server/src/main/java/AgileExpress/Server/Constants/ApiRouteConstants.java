package AgileExpress.Server.Constants;

public class ApiRouteConstants {

    //region Auth Routes

    public static final String SignUp = "/api/auth/signup";

    public static final String GetUsername = "/api/auth/username";

    //endregion

    //region User Routes

    public static final String GetUsers = "/api/users";

    public static final String GetUser = "/api/users/{username}";

    //endregion

    //region Project Routes

    public static final String GetProject = "/api/projects/{projectID}";
    public static final String GetProjects = "/api/projects";
    public static final String ProjectUser = "/api/projects/user";

    public static final String ProjectsSprint = "/api/projects/sprint";
    public static final String ProjectsTask = "/api/projects/task";
    public static final String ProjectsTasks = "/api/projects/tasks";
    public static final String ProjectsTaskPath = "/api/projects/task/{taskID}";
    public static final String ProjectsTaskComment = "/api/projects/task/comments";
    public static final String ProjectTaskAssignee = "/api/projects/task/assignees";
    public static final String ProjectTaskAssigneePath = "/api/projects/task/assignees/{userID}";
    public static final String ProjectTaskLabel = "/api/project/task/labels";
    //endregion
}

package AgileExpress.Server.Constants;

public class ApiRouteConstants {

    //region Auth Routes

    public static final String SignUp = "/api/auth/signup";

    public static final String GetUsername = "/api/auth/username";

    public static final String GetAccessLevel = "/api/auth/accesslevel";

    public static final String GetAuthenticatedUser = "/api/auth/user";

    //endregion

    //region User Routes

    public static final String GetUsers = "/api/users";

    public static final String GetUser = "/api/users/{username}";

    public static final String GetAuthLevel = "/api/users/{userID}";

    //endregion

    //region Project Routes

    public static final String Project = "/api/projects/{projectID}";
    public static final String Projects = "/api/projects";
    public static final String ProjectUser = "/api/projects/user";

    public static final String ProjectTeamMembers = "/api/projects/{projectID}/users";

    public static final String ProjectManager = "/api/projects/manager";

    public static final String ProjectsSprint = "/api/projects/sprints";

    public static final String ProjectsSprintPath = "/api/projects/sprints/{sprintID}";
    public static final String ProjectsTask = "/api/projects/task";
    public static final String ProjectsTasks = "/api/projects/tasks";
    public static final String ProjectsTaskPath = "/api/projects/task/{taskID}";
    public static final String ProjectsTaskComment = "/api/projects/task/comments";
    public static final String ProjectTaskAssignee = "/api/projects/task/assignees";

    public static final String ProjectTaskStatus = "/api/projects/task/status";
    public static final String ProjectTaskAssigneePath = "/api/projects/task/assignees/{userID}";
    public static final String ProjectTaskLabel = "/api/projects/task/labels";

    public static final String ProjectsTasksAssign = "/api/projects/task/assign";

    public static final String SprintActive = "/api/projects/sprints/active";

    //endregion

    //region Search

    public static final String Search = "/api/search";

    //endregion
}

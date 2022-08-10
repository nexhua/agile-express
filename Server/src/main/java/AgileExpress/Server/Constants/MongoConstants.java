package AgileExpress.Server.Constants;

public class MongoConstants {

    public static final String Unassigned = "unassigned";

    public static final String Id = "_id";

    public static final String Users = "users";

    public static final String Projects = "projects";
    public static final String ProjectTeamMembers = "teamMembers";
    public static final String NoDescription = "No Description Provided";

    public static final String Class = "_class";

    //region Projects

    public static final String ProjectName = "projectName";

    public static final String StartDate = "startDate";

    public static final String EndDate = "endDate";

    public static final String StatusFields = "statusFields";

    public static final String Tasks = "tasks";

    public static final String TeamMembers = "teamMembers";

    public static final String ProjectRole = "projectRole";

    public static final String TeamMembersOfProject = "projectTeamMembers";

    //endregion

    //region Tasks

    public static final String Name = "name";

    public static final String Description = "description";

    public static final String CreatedBy = "createdBy";

    public static final String CreatedAt = "createdAt";

    public static final String StoryPoint = "storyPoint";

    public static final String CurrentStatus = "currentStatus";

    public static final String Sprint = "sprints";

    public static final String SprintID = "sprintID";

    public static final String Comments = "comments";

    public static final String Assignees = "assignees";

    public static final String Labels = "labels";

    public static final String UserID = "userID";

    //endregion

    //region Sprints

    public static final String Active = "active";

    public static final String IsClosed = "isClosed";

    //endregion


    //region Users

    public static final String UserProjects = "userProjects";

    //enderegion
}

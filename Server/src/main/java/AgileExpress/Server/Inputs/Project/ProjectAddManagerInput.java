package AgileExpress.Server.Inputs.Project;

import AgileExpress.Server.Constants.UserTypes;

public class ProjectAddManagerInput extends BaseProjectInput {

    private String userID;

    private UserTypes projectRole;

    public ProjectAddManagerInput() { }

    public ProjectAddManagerInput(String projectID, String userID, UserTypes projectRole) {
        super(projectID);
        this.userID = userID;
        this.projectRole = projectRole;
    }

    //region Getters and Setters

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public UserTypes getProjectRole() {
        return projectRole;
    }

    public void setProjectRole(UserTypes projectRole) {
        this.projectRole = projectRole;
    }

    //endregion
}

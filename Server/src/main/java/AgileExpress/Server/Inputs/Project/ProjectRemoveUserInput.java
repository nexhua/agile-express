package AgileExpress.Server.Inputs.Project;

public class ProjectRemoveUserInput {

    private String projectID;

    private String userID;

    public ProjectRemoveUserInput() { }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}

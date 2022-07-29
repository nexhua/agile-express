package AgileExpress.Server.Inputs.Project;

public class ProjectRemoveUserInput extends BaseProjectInput {


    private String userID;

    public ProjectRemoveUserInput() { }

    public ProjectRemoveUserInput(String projectID, String userID) {
        super(projectID);
        this.userID = userID;
    }

    //region Getters and Setters

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    //endregion
}

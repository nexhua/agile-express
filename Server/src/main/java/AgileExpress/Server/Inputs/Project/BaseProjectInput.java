package AgileExpress.Server.Inputs.Project;

public class BaseProjectInput {

    private String projectID;

    public BaseProjectInput() { }

    public BaseProjectInput(String projectID) {
        this.projectID = projectID;
    }

    //region Getters and Setter

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    //endregion
}

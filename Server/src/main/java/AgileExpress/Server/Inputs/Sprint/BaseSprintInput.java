package AgileExpress.Server.Inputs.Sprint;

public class BaseSprintInput {

    private String projectID;

    public BaseSprintInput() {

    }

    public BaseSprintInput(String projectID) {
        this.projectID = projectID;
    }

    //region Getters and Setters

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    //endregion
}

package AgileExpress.Server.Inputs.Project;

public class ProjectGetTasksInput {

    private String projectID;

    public ProjectGetTasksInput() { }

    public ProjectGetTasksInput(String projectID) {
        this.projectID = projectID;
    }

    //region Getter and Setters

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    //endregion
}

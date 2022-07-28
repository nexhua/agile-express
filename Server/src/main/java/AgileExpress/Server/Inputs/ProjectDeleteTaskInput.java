package AgileExpress.Server.Inputs;

public class ProjectDeleteTaskInput {

    private String projectID;

    private String taskID;

    public ProjectDeleteTaskInput() { }

    public ProjectDeleteTaskInput(String projectID, String taskID) {
        this.projectID = projectID;
        this.taskID = taskID;
    }

    //region Getter and Setters

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    //endregion
}

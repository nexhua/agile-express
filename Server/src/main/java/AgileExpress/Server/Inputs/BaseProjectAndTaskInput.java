package AgileExpress.Server.Inputs;

public class BaseProjectAndTaskInput {

    private String projectID;

    private String taskID;

    public BaseProjectAndTaskInput() { }

    public BaseProjectAndTaskInput(String projectID, String taskID) {
        this.projectID = projectID;
        this.taskID = taskID;
    }

    //region Getters and Setters

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

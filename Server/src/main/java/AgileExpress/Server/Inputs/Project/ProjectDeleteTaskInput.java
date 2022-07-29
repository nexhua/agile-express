package AgileExpress.Server.Inputs.Project;

public class ProjectDeleteTaskInput extends BaseProjectInput {


    private String taskID;

    public ProjectDeleteTaskInput() { }

    public ProjectDeleteTaskInput(String projectID, String taskID) {
        super(projectID);
        this.taskID = taskID;
    }

    //region Getter and Setters


    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    //endregion
}

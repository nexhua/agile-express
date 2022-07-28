package AgileExpress.Server.Inputs.Task;

public class TaskDeleteAssigneeInput extends BaseProjectAndTaskInput {

    private String assigneeID;

    public TaskDeleteAssigneeInput() {
    }

    public TaskDeleteAssigneeInput(String projectID, String taskID, String assigneeID) {
        super(projectID, taskID);
        this.assigneeID = assigneeID;
    }

    //region Getters and Setters

    public String getAssigneeID() {
        return assigneeID;
    }

    public void setAssigneeID(String assigneeID) {
        this.assigneeID = assigneeID;
    }

    //endregion
}

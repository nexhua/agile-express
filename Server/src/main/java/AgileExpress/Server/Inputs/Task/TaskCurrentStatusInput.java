package AgileExpress.Server.Inputs.Task;

public class TaskCurrentStatusInput extends BaseProjectAndTaskInput {

    private Number currentStatus;

    public TaskCurrentStatusInput() {} ;

    public TaskCurrentStatusInput(String projectID, String taskID, int currentStatus) {
        super(projectID, taskID);
        this.currentStatus = currentStatus;
    }

    //region Getters and Setters

    public Number getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Number currentStatus) {
        this.currentStatus = currentStatus;
    }

    //endregion
}

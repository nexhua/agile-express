package AgileExpress.Server.Inputs.Task;

public class TaskSprintAssignInput extends BaseProjectAndTaskInput {

    private String sprintID;

    public TaskSprintAssignInput() { }

    public TaskSprintAssignInput(String projectID, String taskID, String sprintID) {
        super(projectID, taskID);
        this.sprintID = sprintID;
    }

    //region Getters and Setters

    public String getSprintID() {
        return sprintID;
    }

    public void setSprintID(String sprintID) {
        this.sprintID = sprintID;
    }

    //endregion
}

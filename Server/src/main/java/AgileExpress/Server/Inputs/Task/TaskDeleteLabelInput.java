package AgileExpress.Server.Inputs.Task;

public class TaskDeleteLabelInput extends BaseProjectAndTaskInput {

    private String labelID;

    public TaskDeleteLabelInput() {
    }

    public TaskDeleteLabelInput(String projectID, String taskID, String labelID) {
        super(projectID, taskID);
        this.labelID = labelID;
    }

    //region Getter and Setters

    public String getLabelID() {
        return labelID;
    }

    public void setLabelID(String labelID) {
        this.labelID = labelID;
    }

    //endregion
}

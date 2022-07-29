package AgileExpress.Server.Inputs.Sprint;

public class SprintDeleteInput extends BaseSprintInput {

    private String sprintID;


    public SprintDeleteInput() {

    }

    public SprintDeleteInput(String projectID, String sprintID) {
        super(projectID);
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

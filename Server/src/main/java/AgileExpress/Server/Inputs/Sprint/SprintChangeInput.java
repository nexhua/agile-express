package AgileExpress.Server.Inputs.Sprint;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class SprintChangeInput {

    private String projectID;

    private Date startDate;

    private Date endDate;

    private String goal;

    @JsonProperty("isClosed")
    private boolean isClosed;


    public SprintChangeInput() { }

    public SprintChangeInput(String projectID, String sprintID, Date startDate, Date endDate, String goal, boolean isClosed) {
        this.projectID = projectID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.goal = goal;
        this.isClosed = isClosed;
    }

    //region Getters and Setters

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }


    //endregion
}

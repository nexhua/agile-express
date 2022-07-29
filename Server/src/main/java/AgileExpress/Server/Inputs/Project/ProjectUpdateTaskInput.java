package AgileExpress.Server.Inputs.Project;

import AgileExpress.Server.Entities.Assignee;
import AgileExpress.Server.Entities.Comment;

import java.util.Collections;
import java.util.List;

public class ProjectUpdateTaskInput {

    private String projectID;
    private String name;
    private String description;
    private int storyPoint = 0;
    private Number currentStatus = 0;
    private String sprint;

    public ProjectUpdateTaskInput() { }

    public ProjectUpdateTaskInput(String projectID, String name, String description, int storyPoint, Number currentStatus, String sprint) {
        this.projectID = projectID;
        this.name = name;
        this.description = description;
        this.storyPoint = storyPoint;
        this.currentStatus = currentStatus;
        this.sprint = sprint;
    }

    //region Getter and Setters

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStoryPoint() {
        return storyPoint;
    }

    public void setStoryPoint(int storyPoint) {
        this.storyPoint = storyPoint;
    }

    public Number getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Number currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getSprint() {
        return sprint;
    }

    public void setSprint(String sprint) {
        this.sprint = sprint;
    }

    //endregion
}

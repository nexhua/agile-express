package AgileExpress.Server.Inputs;

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
    private List<Comment> comments = Collections.emptyList();
    private List<Assignee> assignees = Collections.emptyList();
    private List<String> labels = Collections.emptyList();

    public ProjectUpdateTaskInput() { }

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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Assignee> getAssignees() {
        return assignees;
    }

    public void setAssignees(List<Assignee> assignees) {
        this.assignees = assignees;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }


    //endregion
}

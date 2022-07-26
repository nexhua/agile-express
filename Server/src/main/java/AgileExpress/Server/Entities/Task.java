package AgileExpress.Server.Entities;

import AgileExpress.Server.Constants.MongoConstants;
import org.bson.Document;
import org.bson.types.ObjectId;


import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Task extends TaskBase {

    private String name;
    private String description;
    private String createdBy;
    private Date createdAt;
    private int storyPoint = 20;
    private Number currentStatus = 0;
    private String sprint;
    private List<Comment> comments = Collections.emptyList();
    private List<Assignee> assignees = Collections.emptyList();
    private List<String> labels = Collections.emptyList();

    public Task() {
    }

    public Task(String name, String description, String createdBy, int storyPoint) {
        this.name = name;
        this.setDescription(description);
        this.createdBy = createdBy;
        this.storyPoint = storyPoint;
        this.createdAt = new Date();
        this.sprint = MongoConstants.Unassigned;
    }

    public Document toDocument() {
        return new Document("_id", new ObjectId())
                .append("name", this.getName())
                .append("description", this.getDescription())
                .append("createdBy", this.getCreatedBy())
                .append("createdAt", this.getCreatedAt())
                .append("storyPoint", this.getStoryPoint())
                .append("currentStatus", this.getCurrentStatus())
                .append("sprint", this.getSprint())
                .append("comments", this.getComments())
                .append("assignees", this.getAssignees())
                .append("labels", this.getLabels());
    }

    //region Getter and Setters

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
        if (description.isEmpty()) {
            this.description = MongoConstants.NoDescription;
        } else {
            this.description = description;
        }
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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

    public String getSprint() {
        return sprint;
    }

    public void setSprint(String sprint) {
        this.sprint = sprint;
    }

//endregion
}

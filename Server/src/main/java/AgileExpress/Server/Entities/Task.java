package AgileExpress.Server.Entities;

import AgileExpress.Server.Constants.MongoConstants;
import AgileExpress.Server.Utility.IDGenerationPolicy;
import org.bson.BsonNull;


import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public class Task {

    @Id
    @GeneratedValue
    private String id;
    private String name;
    private String description;
    private String createdBy;
    private Date createdAt;
    private int storyPoint = 20;
    private Number currentStatus = 0;

    @Id
    @IDGenerationPolicy(generate = false)
    private String sprintID;
    private List<Comment> comments = Collections.emptyList();
    private List<Assignee> assignees = Collections.emptyList();
    private List<Label> labels = Collections.emptyList();

    public Task() {
    }

    public Task(String name, String description, String createdBy, int storyPoint) {
        this.name = name;
        this.setDescription(description);
        this.createdBy = createdBy;
        this.storyPoint = storyPoint;
        this.createdAt = new Date();
        this.sprintID = BsonNull.VALUE.toString();
    }

    public Optional<Assignee> getAssignee(String userID) {
        return this.getAssignees().stream().filter(a -> a.getUserID().equals(userID)).findFirst();
    }

    //region Getter and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public String getSprint() {
        return sprintID;
    }

    public void setSprint(String sprint) {
        this.sprintID = sprint;
    }

//endregion
}

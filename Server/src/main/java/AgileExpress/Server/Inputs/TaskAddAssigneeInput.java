package AgileExpress.Server.Inputs;

import AgileExpress.Server.Entities.Assignee;
import AgileExpress.Server.Helpers.ReflectionHelper;
import AgileExpress.Server.Interfaces.IToInputDocument;
import AgileExpress.Server.Interfaces.IToInputObject;
import org.bson.Document;

public class TaskAddAssigneeInput implements IToInputObject<Assignee>, IToInputDocument {

    private String projectID;

    private String taskID;

    private String userID;

    private String username;

    private String assignedBy;

    public TaskAddAssigneeInput() {
    }

    public TaskAddAssigneeInput(String projectID, String taskID, String userID, String assignedBy, String username) {
        this.projectID = projectID;
        this.taskID = taskID;
        this.userID = userID;
        this.assignedBy = assignedBy;
        this.username = username;
    }

    @Override
    public Assignee toObject() {
        return new Assignee(this.getUserID(), this.getAssignedBy(), this.getUsername());
    }

    @Override
    public Document toDocument() {
        return ReflectionHelper.toDocument(this.toObject());
    }

    //region Getter and Setters

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(String assignedBy) {
        this.assignedBy = assignedBy;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //endregion
}

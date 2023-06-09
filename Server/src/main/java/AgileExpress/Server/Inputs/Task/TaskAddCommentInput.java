package AgileExpress.Server.Inputs.Task;

import AgileExpress.Server.Constants.CommentTypes;
import AgileExpress.Server.Entities.Comment;
import AgileExpress.Server.Helpers.ReflectionHelper;
import AgileExpress.Server.Interfaces.IToInputDocument;
import AgileExpress.Server.Interfaces.IToInputObject;
import org.bson.Document;

public class TaskAddCommentInput implements IToInputObject<Comment>, IToInputDocument {

    private String projectID;

    private String taskID;

    private String username;

    private String comment;

    private CommentTypes action;

    public TaskAddCommentInput() { }

    public TaskAddCommentInput(String projectID, String taskID, String username, String comment) {
        this.projectID = projectID;
        this.taskID = taskID;
        this.username = username;
        this.comment = comment;
    }

    @Override
    public Comment toObject() {
        return new Comment(this.getUsername(), this.getComment(), this.getAction());
    }

    @Override
    public Document toDocument() {
        return ReflectionHelper.toDocument(this.toObject(), null);
    }

    //region Getters and Setters

    public CommentTypes getAction() {
        return action;
    }

    public void setAction(CommentTypes action) {
        this.action = action;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    //endregion
}

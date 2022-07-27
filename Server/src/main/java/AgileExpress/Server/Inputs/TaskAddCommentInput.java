package AgileExpress.Server.Inputs;

import AgileExpress.Server.Entities.Comment;

public class TaskAddCommentInput {

    private String projectID;

    private String taskID;

    private String username;

    private String comment;

    public TaskAddCommentInput() { }

    public TaskAddCommentInput(String projectID, String taskID, String username, String comment) {
        this.projectID = projectID;
        this.taskID = taskID;
        this.username = username;
        this.comment = comment;
    }

    public Comment toComment() {
        return new Comment(this.getUsername(), this.getComment());
    }

    //region Getters and Setters

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

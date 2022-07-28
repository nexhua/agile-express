package AgileExpress.Server.Inputs;

import AgileExpress.Server.Entities.Comment;
import AgileExpress.Server.Interfaces.IToInputObject;

public class TaskDeleteCommentInput extends BaseProjectAndTaskInput {

    private String commentID;

    public TaskDeleteCommentInput() { }

    public TaskDeleteCommentInput(String projectID, String taskID, String commentID) {
        super(projectID, taskID);
        this.commentID = commentID;
    }



    //region Getters and Setters

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    //endregion
}

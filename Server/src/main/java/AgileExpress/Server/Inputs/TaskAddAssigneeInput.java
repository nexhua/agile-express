package AgileExpress.Server.Inputs;

import AgileExpress.Server.Entities.Assignee;
import AgileExpress.Server.Helpers.ReflectionHelper;
import AgileExpress.Server.Interfaces.IToInputDocument;
import AgileExpress.Server.Interfaces.IToInputObject;
import org.bson.Document;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TaskAddAssigneeInput extends BaseProjectAndTaskInput implements IToInputObject<Assignee>, IToInputDocument {


    private String userID;


    private String assignedBy;

    public TaskAddAssigneeInput() {
    }

    public TaskAddAssigneeInput(String projectID, String taskID, String userID, String assignedBy) {
        super(projectID, taskID);
        this.userID = userID;
        this.assignedBy = assignedBy;
    }

    @Override
    public Assignee toObject() {
        return new Assignee(this.getUserID(), this.getAssignedBy());
    }

    @Override
    public Document toDocument() {
        Queue<String> idQueue = new ConcurrentLinkedQueue<>();
        idQueue.add(this.getUserID());
        return ReflectionHelper.toDocument(this.toObject(), idQueue);
    }

    //region Getter and Setters

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

    //endregion
}

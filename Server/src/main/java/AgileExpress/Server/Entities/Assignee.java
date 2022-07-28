package AgileExpress.Server.Entities;

import AgileExpress.Server.Utility.IDGenerationPolicy;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

public class Assignee {

    @Id
    @GeneratedValue
    private String id;

    @Id
    @IDGenerationPolicy(generate = false)
    private String userID;
    private String assignedBy;
    private Date assignedAt;

    public Assignee() { }

    public Assignee(String userID, String assignedBy) {
        this.userID = userID;
        this.assignedBy = assignedBy;
        this.assignedAt = new Date();
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

    public Date getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(Date assignedAt) {
        this.assignedAt = assignedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //endregion
}

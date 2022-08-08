package AgileExpress.Server.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

public class Sprint {

    @Id
    @GeneratedValue
    private String id;
    private Date startDate;

    private Date endDate;

    private String goal;

    @JsonProperty("isClosed")
    private boolean isClosed;

    private boolean active;

    public Sprint() { }

    public Sprint(Date startDate, Date endDate, String goal) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.goal = goal;
        this.isClosed = false;
        this.active = false;
    }

    //region Getter and Setters

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }


    //endregion
}

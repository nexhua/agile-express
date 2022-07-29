package AgileExpress.Server.Entities;

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

    private boolean isClosed;

    public Sprint() { }

    public Sprint(Date startDate, Date endDate, String goal) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.goal = goal;
        this.isClosed = false;
    }

    //region Getter and Setters

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

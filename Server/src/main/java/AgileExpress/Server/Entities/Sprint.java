package AgileExpress.Server.Entities;

import java.util.Date;

public class Sprint {

    private Date startDate;

    private Date endDate;

    private String goal;

    private boolean isClosed;

    public Sprint() { }

    public Sprint(Date startDate, Date endDate, String goal, boolean isClosed) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.goal = goal;
        this.isClosed = isClosed;
    }

    //region Getter and Setters

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

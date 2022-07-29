package AgileExpress.Server.Inputs.Sprint;

import AgileExpress.Server.Entities.Sprint;
import AgileExpress.Server.Helpers.ReflectionHelper;
import AgileExpress.Server.Interfaces.IToInputDocument;
import AgileExpress.Server.Interfaces.IToInputObject;
import org.bson.Document;

import java.util.Date;

public class SprintCreateInput extends BaseSprintInput implements IToInputObject<Sprint>, IToInputDocument {

    private Date startDate;

    private Date endDate;

    private String goal;

    public SprintCreateInput() {
    }

    public SprintCreateInput(String projectID, Date startDate, Date endDate, String goal) {
        super(projectID);
        this.startDate = startDate;
        this.endDate = endDate;
        this.goal = goal;
    }

    @Override
    public Sprint toObject() {
        return new Sprint(this.getStartDate(), this.getEndDate(), this.getGoal());
    }

    @Override
    public Document toDocument() {
        return ReflectionHelper.toDocument(this.toObject(), null);
    }

    //region Getters and Setters

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


    //endregion
}

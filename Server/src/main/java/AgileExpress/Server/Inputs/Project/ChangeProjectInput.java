package AgileExpress.Server.Inputs.Project;

import AgileExpress.Server.Utility.IncludeEmpty;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ChangeProjectInput extends BaseProjectInput {



    private String projectName;

    private Date startDate;

    private Date endDate;

    @IncludeEmpty
    private List<String> statusFields = Collections.emptyList();

    public ChangeProjectInput() { }

    public ChangeProjectInput(String projectID, String projectName, Date startDate, Date endDate, List<String> statusFields) {
        super(projectID);
        this.projectName = projectName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.statusFields = statusFields;
    }

    //region Getter and Setters

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public List<String> getStatusFields() {
        return statusFields;
    }

    public void setStatusFields(List<String> statusFields) {
        this.statusFields = statusFields;
    }


    //endregion
}

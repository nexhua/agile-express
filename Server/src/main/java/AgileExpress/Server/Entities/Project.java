package AgileExpress.Server.Entities;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document("projects")
public class Project extends ProjectBase {

    private String projectName;

    private Date startDate;

    private Date endDate;

    private List<String> statusFields = Arrays.asList("Backlog");

    private List<TaskBase> tasks = Collections.emptyList();

    private List<UserBase> teamMembers = Collections.emptyList();

    public Project() { }

    public Project(String projectName, Date startDate, Date endDate) {
        this.projectName = projectName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Project(String projectName, Date  startDate, Date endDate, List<String> statusFields, List<TaskBase> tasks, List<UserBase> teamMembers) {
        this.projectName = projectName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.statusFields = statusFields;
        this.tasks = tasks;
        this.teamMembers = teamMembers;
    }

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

    public List<TaskBase> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskBase> tasks) {
        this.tasks = tasks;
    }

    public List<UserBase> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<UserBase> teamMembers) {
        this.teamMembers = teamMembers;
    }
}

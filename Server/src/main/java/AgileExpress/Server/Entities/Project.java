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

    private List<Task> tasks = Collections.emptyList();

    private List<TeamMember> teamMembers = Collections.emptyList();

    private List<Sprint> sprints = Collections.emptyList();

    public Project() { }

    public Project(String projectName, Date startDate, Date endDate) {
        this.projectName = projectName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Project(String projectName, Date  startDate, Date endDate, List<String> statusFields, List<Task> tasks, List<TeamMember> teamMembers) {
        this.projectName = projectName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.statusFields = statusFields;
        this.tasks = tasks;
        this.teamMembers = teamMembers;
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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<TeamMember> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }

    //endregion
}

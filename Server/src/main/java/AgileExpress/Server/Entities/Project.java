package AgileExpress.Server.Entities;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.*;

@Document("projects")
public class Project {

    @Id
    @GeneratedValue
    private String id;
    private String projectName;

    private Date startDate;

    private Date endDate;

    private List<String> statusFields = List.of("Backlog");

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

    public Optional<Task> getTask(String id) {
        return this.getTasks().stream().filter(t-> t.getId().equals(id)).findFirst();
    }

    //region Getter and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Sprint> getSprints() {
        return sprints;
    }

    public void setSprints(List<Sprint> sprints) {
        this.sprints = sprints;
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

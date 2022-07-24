package AgileExpress.Server.Entities;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

    public Project(String projectName, Date startDate, Date endDate, List<String> statusFields, List<TaskBase> tasks, List<UserBase> teamMembers) {
        this.projectName = projectName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.statusFields = statusFields;
        this.tasks = tasks;
        this.teamMembers = teamMembers;
    }
}

package AgileExpress.Server.Entities;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collections;
import java.util.List;

public class ProjectTeamMembers {

    @Id
    @GeneratedValue
    private String id;

    private List<TeamMember> teamMembers = Collections.emptyList();

    private List<User> projectTeamMembers = Collections.emptyList();

    public ProjectTeamMembers() { }

    public ProjectTeamMembers(String id, List<TeamMember> teamMembers, List<User> projectTeamMembers) {
        this.id = id;
        this.teamMembers = teamMembers;
        this.projectTeamMembers = projectTeamMembers;
    }

    //region Getter and Setters

    public List<TeamMember> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<User> getProjectTeamMembers() {
        return projectTeamMembers;
    }

    public void setProjectTeamMembers(List<User> projectTeamMembers) {
        this.projectTeamMembers = projectTeamMembers;
    }


    //endregion
}

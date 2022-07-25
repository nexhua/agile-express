package AgileExpress.Server.Entities;

import AgileExpress.Server.Constants.UserTypes;

import javax.persistence.Id;

public class TeamMember {

    @Id
    private String id;

    private UserTypes projectRole;

    public TeamMember(String id, UserTypes projectRole) {
        this.id = id;
        this.projectRole = projectRole;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserTypes getProjectRole() {
        return projectRole;
    }

    public void setProjectRole(UserTypes projectRole) {
        this.projectRole = projectRole;
    }
}

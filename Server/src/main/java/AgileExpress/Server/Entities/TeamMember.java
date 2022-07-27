package AgileExpress.Server.Entities;

import AgileExpress.Server.Constants.UserTypes;
import org.bson.types.ObjectId;
import org.bson.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class TeamMember {

    @Id
    @GeneratedValue
    private String id;

    private UserTypes projectRole;

    public TeamMember() { }

    public TeamMember(String id, UserTypes projectRole) {
        this.id = id;
        this.projectRole = projectRole;
    }

    //region Getter and Setters

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

    //endregion
}

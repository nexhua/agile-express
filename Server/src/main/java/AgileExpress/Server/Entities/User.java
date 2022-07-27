package AgileExpress.Server.Entities;

import AgileExpress.Server.Constants.UserTypes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collections;
import java.util.List;

@Document(collection = "users")
public class User {

    @Id
    @GeneratedValue
    private String id;
    @Indexed(unique = true)
    private String username;

    @Indexed(unique = true)
    private String email;

    private String name;

    private String surname;

    @Field("role")
    private UserTypes type = UserTypes.TEAM_MEMBER;

    @Field("projects")
    private List<Project> projectIds;

    public User() { }
    public User(String username) {
        this.username = username;
    }

    public User(String username, String email, String name, String surname, UserTypes type) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.type = type;
        this.projectIds = Collections.emptyList();
    }

    //region Getter and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public UserTypes getType() {
        return type;
    }

    public void setType(UserTypes type) {
        this.type = type;
    }

    public List<Project> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<Project> projectIds) {
        this.projectIds = projectIds;
    }

    //endregion
}

package AgileExpress.Server.Entities;

import java.util.List;

public class UserProjects {

    List<Project> userProjects;

    public UserProjects() { }

    public UserProjects(List<Project> userProjects) {
        this.userProjects = userProjects;
    }

    //region Getter and Setters

    public List<Project> getUserProjects() {
        return userProjects;
    }

    public void setUserProjects(List<Project> userProjects) {
        this.userProjects = userProjects;
    }

    //endregion
}

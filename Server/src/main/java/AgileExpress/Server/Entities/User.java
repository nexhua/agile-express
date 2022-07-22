package AgileExpress.Server.Entities;

public class User {

    private String username;

    public User(String username) {
        this.username = username;
    }

    //region Getter and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //endregion
}

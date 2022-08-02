package AgileExpress.Server.Entities;

import AgileExpress.Server.Constants.UserTypes;

public class UserContext {

    private String id;

    private UserTypes type;

    public UserContext() {
        this.id = "";
        this.type = UserTypes.TEAM_MEMBER;
    }

    public UserContext(String id, UserTypes type) {
        this.id = id;
        this.type = type;
    }

    //region Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserTypes getType() {
        return type;
    }

    public void setType(UserTypes type) {
        this.type = type;
    }

    //endregion
}

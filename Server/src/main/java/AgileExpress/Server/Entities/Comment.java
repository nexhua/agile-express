package AgileExpress.Server.Entities;

public class Comment {

    private String username;

    private String comment;

    public Comment() { }

    public Comment(String username, String comment) {
        this.username = username;
        this.comment = comment;
    }

    //region Getter and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    //endregion
}

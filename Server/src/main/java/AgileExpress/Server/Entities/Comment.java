package AgileExpress.Server.Entities;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

public class Comment {

    @Id
    @GeneratedValue
    private String id;
    private String username;
    private String comment;
    private Date date;

    public Comment() { }

    public Comment(String username, String comment) {
        this.username = username;
        this.comment = comment;
        this.date = new Date();
    }

    //region Getter and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    //endregion
}

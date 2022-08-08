package AgileExpress.Server.Entities;

import AgileExpress.Server.Constants.CommentTypes;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

public class Comment {

    @Id
    @GeneratedValue
    private String id;
    private String username;
    private String comment;

    @Field("action")
    private CommentTypes action;
    private Date date;

    public Comment() { }

    public Comment(String username, String comment, CommentTypes action) {
        this.username = username;
        this.comment = comment;
        this.date = new Date();
        this.action = action;
    }

    //region Getter and Setters

    public CommentTypes getAction() {
        return action;
    }

    public void setAction(CommentTypes action) {
        this.action = action;
    }

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

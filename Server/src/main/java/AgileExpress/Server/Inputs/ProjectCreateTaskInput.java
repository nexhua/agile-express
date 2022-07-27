package AgileExpress.Server.Inputs;

import AgileExpress.Server.Entities.Task;
import AgileExpress.Server.Helpers.ReflectionHelper;
import AgileExpress.Server.Interfaces.IToInputDocument;
import AgileExpress.Server.Interfaces.IToInputObject;
import org.bson.Document;

import java.util.Date;

public class ProjectCreateTaskInput implements IToInputObject<Task>, IToInputDocument {

    private String projectID;
    private String name;
    private String description;
    private String createdBy;
    private int storyPoint = 20;

    public ProjectCreateTaskInput() {
    }

    public ProjectCreateTaskInput(String projectID, String name, String description, String createdBy, int storyPoint) {
        this.projectID = projectID;
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
        this.setStoryPoint(storyPoint);
    }

    @Override
    public Task toObject() {
        return new Task(this.getName(), this.getDescription(), this.getCreatedBy(), this.getStoryPoint());
    }

    public Document toDocument() {
        return ReflectionHelper.toDocument(this.toObject());
    }

    //region Getter and Setters

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public int getStoryPoint() {
        return storyPoint;
    }

    public void setStoryPoint(int storyPoint) {
        if (storyPoint > 0) {
            this.storyPoint = storyPoint;
        }
        else {
            this.storyPoint = 0;
        }
    }

    //endregion
}

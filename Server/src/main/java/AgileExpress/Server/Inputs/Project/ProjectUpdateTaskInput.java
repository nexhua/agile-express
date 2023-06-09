package AgileExpress.Server.Inputs.Project;

public class ProjectUpdateTaskInput {

    private String projectID;
    private String name;
    private String description;
    private int storyPoint = 0;

    private String sprintID;

    public ProjectUpdateTaskInput() { }

    public ProjectUpdateTaskInput(String projectID, String name, String description, int storyPoint, String sprintID) {
        this.projectID = projectID;
        this.name = name;
        this.description = description;
        this.storyPoint = storyPoint;
        this.sprintID = sprintID;
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

    public int getStoryPoint() {
        return storyPoint;
    }

    public void setStoryPoint(int storyPoint) {
        this.storyPoint = storyPoint;
    }

    public String getSprint() {
        return sprintID;
    }

    public void setSprint(String sprint) {
        this.sprintID = sprint;
    }

    //endregion
}

package AgileExpress.Server.Inputs;

import AgileExpress.Server.Constants.UserTypes;
import AgileExpress.Server.Entities.TeamMember;
import AgileExpress.Server.Helpers.ReflectionHelper;
import AgileExpress.Server.Interfaces.IToInputObject;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProjectAddUserInput {

    private List<String> userIds = Collections.emptyList();

    private String projectId;

    public ProjectAddUserInput() {

    }

    public boolean Add(String id) {
        if (!this.userIds.contains(id)) {
            this.userIds.add(id);
            return true;
        }
        return false;
    }


    public List<Document> ToDocumentArray() {
        List<Document> members = new ArrayList<>();
        for(String id : userIds) {
            members.add(ReflectionHelper.toDocument(new TeamMember(id, UserTypes.TEAM_MEMBER)));
        }
        return members;
    }

    //region Getter and Setters

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    //endregion
}

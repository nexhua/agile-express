package AgileExpress.Server.Inputs.Project;

import AgileExpress.Server.Constants.UserTypes;
import AgileExpress.Server.Entities.TeamMember;
import AgileExpress.Server.Interfaces.IToInputObject;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProjectAddUserInput extends BaseProjectInput {

    private List<String> userIds = Collections.emptyList();

    public ProjectAddUserInput() {

    }

    public ProjectAddUserInput(String projectID, List<String> userIds) {
        super(projectID);
        this.userIds = userIds;
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
            members.add(new TeamMember(id, UserTypes.TEAM_MEMBER).toDocument());
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

    //endregion
}

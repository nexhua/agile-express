package AgileExpress.Server.Repositories;

import AgileExpress.Server.Inputs.ProjectAddUserInput;
import AgileExpress.Server.Inputs.ProjectCreateTaskInput;
import AgileExpress.Server.Inputs.ProjectRemoveUserInput;
import AgileExpress.Server.Inputs.TaskAddCommentInput;
import AgileExpress.Server.Utility.PropertyInfo;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.ArrayList;

public interface ProjectRepositoryCustom {

    UpdateResult addTeamMember(ProjectAddUserInput input);

    UpdateResult removeTeamMember(ProjectRemoveUserInput input);

    UpdateResult addTask(ProjectCreateTaskInput input);

    Document updateTask(String projectID, String taskID, ArrayList<PropertyInfo<?>> propertyInfoList);

    UpdateResult addCommentToTask(TaskAddCommentInput input);
}

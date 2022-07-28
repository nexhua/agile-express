package AgileExpress.Server.Repositories;

import AgileExpress.Server.Entities.Task;
import AgileExpress.Server.Inputs.*;
import AgileExpress.Server.Utility.PropertyInfo;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Optional;

public interface ProjectRepositoryCustom {

    UpdateResult addTeamMember(ProjectAddUserInput input);

    UpdateResult removeTeamMember(ProjectRemoveUserInput input);


    UpdateResult addTask(ProjectCreateTaskInput input);

    Document updateTask(String projectID, String taskID, ArrayList<PropertyInfo<?>> propertyInfoList);

    UpdateResult deleteTask(ProjectDeleteTaskInput input);


    UpdateResult addCommentToTask(TaskAddCommentInput input);

    UpdateResult removeCommentFromTask(TaskDeleteCommentInput input);

    UpdateResult addAssigneeToTask(TaskAddAssigneeInput input);
}

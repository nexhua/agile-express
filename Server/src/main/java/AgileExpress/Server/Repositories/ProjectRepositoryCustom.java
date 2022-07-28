package AgileExpress.Server.Repositories;

import AgileExpress.Server.Inputs.Project.ProjectAddUserInput;
import AgileExpress.Server.Inputs.Project.ProjectCreateTaskInput;
import AgileExpress.Server.Inputs.Project.ProjectDeleteTaskInput;
import AgileExpress.Server.Inputs.Project.ProjectRemoveUserInput;
import AgileExpress.Server.Inputs.Task.TaskAddAssigneeInput;
import AgileExpress.Server.Inputs.Task.TaskAddCommentInput;
import AgileExpress.Server.Inputs.Task.TaskDeleteCommentInput;
import AgileExpress.Server.Utility.PropertyInfo;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.ArrayList;

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

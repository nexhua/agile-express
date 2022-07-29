package AgileExpress.Server.Repositories;

import AgileExpress.Server.Inputs.Project.*;
import AgileExpress.Server.Inputs.Sprint.SprintCreateInput;
import AgileExpress.Server.Inputs.Sprint.SprintDeleteInput;
import AgileExpress.Server.Inputs.Task.*;
import AgileExpress.Server.Utility.PropertyInfo;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.ArrayList;

public interface ProjectRepositoryCustom {

    UpdateResult addTeamMember(ProjectAddUserInput input);

    UpdateResult removeTeamMember(ProjectRemoveUserInput input);


    Document updateProject(String projectID, ArrayList<PropertyInfo<?>> propertyInfoList);

    DeleteResult deleteProject(BaseProjectInput input);

    UpdateResult addTask(ProjectCreateTaskInput input);

    Document updateTask(String projectID, String taskID, ArrayList<PropertyInfo<?>> propertyInfoList);

    UpdateResult deleteTask(ProjectDeleteTaskInput input);


    UpdateResult addCommentToTask(TaskAddCommentInput input);

    UpdateResult removeCommentFromTask(TaskDeleteCommentInput input);

    UpdateResult addAssigneeToTask(TaskAddAssigneeInput input);

    UpdateResult removeAssigneeFromTask(TaskDeleteAssigneeInput input);

    UpdateResult addLabelToTask(TaskAddLabelInput input);

    UpdateResult removeLabelFromTask(TaskDeleteLabelInput input);

    UpdateResult addSprint(SprintCreateInput input);

    UpdateResult deleteSprint(SprintDeleteInput input);
}

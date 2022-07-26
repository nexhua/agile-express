package AgileExpress.Server.Repositories;

import AgileExpress.Server.Inputs.ProjectAddUserInput;
import AgileExpress.Server.Inputs.ProjectCreateTaskInput;
import AgileExpress.Server.Inputs.ProjectRemoveUserInput;
import AgileExpress.Server.Inputs.ProjectUpdateTaskInput;
import AgileExpress.Server.Utility.PropertyInfo;
import com.mongodb.client.result.UpdateResult;

import java.util.ArrayList;
import java.util.HashMap;

public interface ProjectRepositoryCustom {

    UpdateResult addTeamMember(ProjectAddUserInput input);

    UpdateResult removeTeamMember(ProjectRemoveUserInput input);

    UpdateResult addTask(ProjectCreateTaskInput input);

    UpdateResult updateTask(String projectID, String taskID, ArrayList<PropertyInfo<?>> propertyInfoList);
}

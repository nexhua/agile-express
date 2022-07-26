package AgileExpress.Server.Repositories;

import AgileExpress.Server.Inputs.ProjectAddUserInput;
import AgileExpress.Server.Inputs.ProjectCreateTaskInput;
import AgileExpress.Server.Inputs.ProjectRemoveUserInput;
import com.mongodb.client.result.UpdateResult;

public interface ProjectRepositoryCustom {

    UpdateResult addTeamMember(ProjectAddUserInput input);

    UpdateResult removeTeamMember(ProjectRemoveUserInput input);

    UpdateResult addTask(ProjectCreateTaskInput input);
}

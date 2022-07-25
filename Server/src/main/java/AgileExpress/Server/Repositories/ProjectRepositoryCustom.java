package AgileExpress.Server.Repositories;

import AgileExpress.Server.Inputs.ProjectAddUserInput;
import com.mongodb.client.result.UpdateResult;

public interface ProjectRepositoryCustom {

    UpdateResult findByProjectName(ProjectAddUserInput input);
}

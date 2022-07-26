package AgileExpress.Server.Repositories;

import AgileExpress.Server.Constants.MongoConstants;
import AgileExpress.Server.Inputs.ProjectAddUserInput;
import AgileExpress.Server.Inputs.ProjectCreateTaskInput;
import AgileExpress.Server.Inputs.ProjectRemoveUserInput;
import com.mongodb.MongoException;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;


public class ProjectRepositoryImpl implements ProjectRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ProjectRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public UpdateResult addTeamMember(ProjectAddUserInput input) {
        UpdateResult result;
        try {
            result = mongoTemplate.getCollection(MongoConstants.Projects)
                    .updateOne(Filters.eq(MongoConstants.Id, new ObjectId(input.getProjectId())),
                            Updates.addEachToSet(MongoConstants.ProjectTeamMembers, input.ToDocumentArray()));
        } catch (MongoException e) {
            result = UpdateResult.unacknowledged();
        }
        return result;
    }

    @Override
    public UpdateResult removeTeamMember(ProjectRemoveUserInput input) {
        UpdateResult result;
        try {
            result = mongoTemplate.getCollection(MongoConstants.Projects)
                    .updateOne(Filters.eq(MongoConstants.Id, new ObjectId(input.getProjectID())),
                            Updates.pull(MongoConstants.ProjectTeamMembers, new Document("_id", new ObjectId(input.getUserID()))));
        } catch (MongoException e) {
            result = UpdateResult.unacknowledged();
        }
        return result;
    }

    @Override
    public UpdateResult addTask(ProjectCreateTaskInput input) {
        UpdateResult result;
        try {
            result = mongoTemplate.getCollection(MongoConstants.Projects)
                    .updateOne(Filters.eq(MongoConstants.Id, new ObjectId(input.getProjectID())),
                            Updates.push(MongoConstants.ProjectTasks, input.toDocument()));
        } catch (MongoException e) {
            result = UpdateResult.unacknowledged();
        }
        return result;
    }
}

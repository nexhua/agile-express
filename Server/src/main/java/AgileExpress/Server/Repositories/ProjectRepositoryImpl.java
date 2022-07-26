package AgileExpress.Server.Repositories;

import AgileExpress.Server.Entities.Project;
import AgileExpress.Server.Inputs.ProjectAddUserInput;
import AgileExpress.Server.Inputs.ProjectRemoveUserInput;
import AgileExpress.Server.Repositories.ProjectRepositoryCustom;
import com.mongodb.MongoException;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;


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
            result = mongoTemplate.getCollection("projects")
                    .updateOne(Filters.eq("_id", new ObjectId(input.getProjectId())),
                            Updates.addEachToSet("teamMembers", input.ToDocumentArray()));
        } catch (MongoException e) {
            result = UpdateResult.unacknowledged();
        }
        return result;
    }

    @Override
    public UpdateResult removeTeamMember(ProjectRemoveUserInput input) {
        UpdateResult result;
        try {
            result = mongoTemplate.getCollection("projects")
                    .updateOne(Filters.eq("_id", new ObjectId(input.getProjectID())),
                            Updates.pull("teamMembers", new Document("_id", new ObjectId(input.getUserID()))));
        } catch (MongoException e) {
            result = UpdateResult.unacknowledged();
        }
        return result;
    }
}

package AgileExpress.Server.Repositories;

import AgileExpress.Server.Entities.Project;
import AgileExpress.Server.Inputs.ProjectAddUserInput;
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
    public UpdateResult findByProjectName(ProjectAddUserInput input) {
        /*
        MatchOperation matchOperation = Aggregation.match(new Criteria(new ObjectId(input.getProjectId()).toString()));
        ProjectionOperation projectionOperation = Aggregation.project("projectName");
        Document user = new Document();
        user.append("_id", new ObjectId(input.getUserIds().get(0)));
        //AccumulatorOperators addToSet = Accumulators.addToSet("teamMembers", new Document().append());

        Aggregation aggregation = Aggregation.newAggregation(matchOperation, projectionOperation);

        AggregationResults<Project> output = mongoTemplate.aggregate(aggregation,"projects",Project.class);
        */

        UpdateResult result;
        try {
            result = mongoTemplate.getCollection("projects")
                    .updateOne(Filters.eq("_id", new ObjectId(input.getProjectId())),
                            Updates.addEachToSet("teamMembers", input.ToObjectArray()));
        } catch (MongoException e) {
            result = UpdateResult.unacknowledged();
        }
        return result;
    }
}

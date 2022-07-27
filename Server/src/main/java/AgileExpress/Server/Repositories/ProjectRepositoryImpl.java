package AgileExpress.Server.Repositories;

import AgileExpress.Server.Constants.ErrorMessages;
import AgileExpress.Server.Constants.MongoConstants;
import AgileExpress.Server.Helpers.QueryHelper;
import AgileExpress.Server.Helpers.ReflectionHelper;
import AgileExpress.Server.Inputs.*;
import AgileExpress.Server.Utility.PropertyInfo;
import com.mongodb.MongoException;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.UpdateDefinition;

import java.sql.Ref;
import java.util.ArrayList;


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
                            Updates.push(MongoConstants.Tasks, input.toDocument()));
        } catch (MongoException e) {
            result = UpdateResult.unacknowledged();
        }

        return result;
    }

    @Override
    public Document updateTask(String projectID, String taskID, ArrayList<PropertyInfo<?>> propertyInfoList) {
        Document result;

        ArrayList<Bson> updatePredicates = new ArrayList<>();

        for (PropertyInfo<?> propertyInfo : propertyInfoList) {
            if (propertyInfo.isString() || propertyInfo.isNumeric()) {
                if (!propertyInfo.getPropertyName().equals("projectID")) {
                    String property = QueryHelper.asInnerDocumentArrayProperty(MongoConstants.Tasks, propertyInfo.getPropertyName());
                    updatePredicates.add(Updates.set(property, propertyInfo.getPropertyValue()));
                }
            }
        }

        if (updatePredicates.size() == 0) {
            return new Document(ErrorMessages.Title, ErrorMessages.NoPropertyToUpdateError(updatePredicates.size()));
        }

        try {
            Bson projectFilter = Filters.eq(MongoConstants.Id, new ObjectId(projectID));
            Bson taskFilter = Filters.eq(QueryHelper.asInnerDocumentProperty(MongoConstants.Tasks, MongoConstants.Id),
                    new ObjectId(taskID));
            result = mongoTemplate.getCollection(MongoConstants.Projects)
                    .findOneAndUpdate(Filters.and(projectFilter, taskFilter),
                            Updates.combine(updatePredicates));
        } catch (MongoException e) {
            result = new Document("Error", e.getMessage());
        }
        return result;
    }


    @Override
    public UpdateResult addCommentToTask(TaskAddCommentInput input) {
        UpdateResult result;
        try {
            Bson projectFilter = Filters.eq(MongoConstants.Id, QueryHelper.createID(input.getProjectID()));

            Bson taskFilter = Filters.eq(QueryHelper.asInnerDocumentProperty(MongoConstants.Tasks, MongoConstants.Id), QueryHelper.createID(input.getTaskID()));

            String commentName = QueryHelper
                    .asInnerDocumentArrayProperty(MongoConstants.Tasks, MongoConstants.Comments);

            Bson update = Updates.push(commentName, input.toDocument());
            result = mongoTemplate.getCollection(MongoConstants.Projects)
                    .updateOne(Filters.and(projectFilter, taskFilter), update);
        } catch (MongoException e) {
            result = UpdateResult.unacknowledged();
        }
        return result;
    }

    @Override
    public UpdateResult addAssigneeToTask(TaskAddAssigneeInput input) {
        UpdateResult result;
        try {
            Bson projectFilter = Filters.eq(MongoConstants.Id, QueryHelper.createID(input.getProjectID()));
            Bson taskFilter = Filters.eq(QueryHelper.asInnerDocumentProperty(MongoConstants.Tasks, MongoConstants.Id), QueryHelper.createID(input.getTaskID()));

            String assigneesName = QueryHelper.asInnerDocumentArrayProperty(MongoConstants.Tasks, MongoConstants.Assignees);

            Bson update = Updates.push(assigneesName, input.toDocument());
            result = mongoTemplate.getCollection(MongoConstants.Projects)
                    .updateOne(Filters.and(projectFilter, taskFilter), update);
        } catch (MongoException e) {
            result = UpdateResult.unacknowledged();
        }
        return result;
    }
}

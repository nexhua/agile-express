package AgileExpress.Server.Repositories;

import AgileExpress.Server.Constants.ErrorMessages;
import AgileExpress.Server.Constants.MongoConstants;
import AgileExpress.Server.Entities.Project;
import AgileExpress.Server.Entities.ProjectTeamMembers;
import AgileExpress.Server.Entities.User;
import AgileExpress.Server.Helpers.QueryHelper;
import AgileExpress.Server.Inputs.Project.*;
import AgileExpress.Server.Inputs.Sprint.SprintActiveInput;
import AgileExpress.Server.Inputs.Sprint.SprintChangeInput;
import AgileExpress.Server.Inputs.Sprint.SprintCreateInput;
import AgileExpress.Server.Inputs.Sprint.SprintDeleteInput;
import AgileExpress.Server.Inputs.Task.*;
import AgileExpress.Server.Utility.PropertyInfo;
import com.mongodb.MongoException;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.BsonBoolean;
import org.bson.BsonNull;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;


import java.util.*;


public class ProjectRepositoryImpl implements ProjectRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ProjectRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public List<Project> findProjectsOfUser(String userID) {
        ArrayList<Project> projects = new ArrayList<>();
        try {
            Bson filter = Filters.eq(
                    QueryHelper.asInnerDocumentProperty(MongoConstants.TeamMembers, MongoConstants.Id), QueryHelper.createID(userID));

            FindIterable<Document> result = mongoTemplate.getCollection(MongoConstants.Projects)
                    .find(filter);

            for (Document document : result) {
                projects.add(mongoTemplate.getConverter().read(Project.class, document));
            }
        } catch (MongoException e) {

        }
        return projects;
    }

    @Override
    public Optional<ProjectTeamMembers> getProjectTeamMembers(String projectID) {

        Optional<ProjectTeamMembers> optionalResult;

        List<Bson> operations = new ArrayList<>();

        Bson matchOperation = Aggregates.match(Filters.eq(
                MongoConstants.Id, QueryHelper.createID(projectID)));

        Bson lookupOperation = Aggregates.lookup(
                MongoConstants.Users,
                QueryHelper.asInnerDocumentProperty(MongoConstants.TeamMembers, MongoConstants.Id),
                MongoConstants.Id,
                MongoConstants.TeamMembersOfProject
        );

        Bson projectionOperation = Aggregates.project(Projections.include(
                MongoConstants.TeamMembersOfProject, MongoConstants.ProjectTeamMembers));

        operations.add(matchOperation);
        operations.add(lookupOperation);
        operations.add(projectionOperation);

        try {
            AggregateIterable<Document> aggregateResult = mongoTemplate.getCollection(MongoConstants.Projects)
                    .aggregate(operations);

            Document teamMembersDocument = aggregateResult.first();

            if (teamMembersDocument != null) {
                teamMembersDocument.put(
                        MongoConstants.Class, ProjectTeamMembers.class);

                ProjectTeamMembers projectTeamMembers = mongoTemplate.getConverter().read(ProjectTeamMembers.class, teamMembersDocument);
                optionalResult = Optional.of(projectTeamMembers);
            } else {
                optionalResult = Optional.empty();
            }
        } catch (MongoException e) {
            optionalResult = Optional.empty();
        }

        return optionalResult;
    }

    @Override
    public UpdateResult addTeamMember(ProjectAddUserInput input) {
        UpdateResult result;
        try {
            result = mongoTemplate.getCollection(MongoConstants.Projects)
                    .updateOne(Filters.eq(MongoConstants.Id, new ObjectId(input.getProjectID())),
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
    public Document updateProject(String projectID, ArrayList<PropertyInfo<?>> propertyInfoList) {
        Document result;

        ArrayList<Bson> updateOperations = new ArrayList<>();

        for (PropertyInfo<?> propertyInfo : propertyInfoList) {
            if (propertyInfo.isString() || propertyInfo.isCollection() || propertyInfo.isDate()) {
                if (!propertyInfo.getPropertyName().equals("projectID")) {
                    updateOperations.add(Updates.set(propertyInfo.getPropertyName(), propertyInfo.getPropertyValue()));
                }
            }
        }

        if (updateOperations.size() == 0) {
            return new Document(ErrorMessages.Title, ErrorMessages.NoPropertyToUpdateError(updateOperations.size()));
        }

        try {
            Bson projectFilter = Filters.eq(MongoConstants.Id, QueryHelper.createID(projectID));

            result = mongoTemplate.getCollection(MongoConstants.Projects)
                    .findOneAndUpdate(projectFilter,
                            Updates.combine(updateOperations));
        } catch (MongoException e) {
            result = new Document(ErrorMessages.Title, e.getMessage());
        }
        return result;
    }

    @Override
    public DeleteResult deleteProject(BaseProjectInput input) {
        DeleteResult result;
        try {
            Bson projectFilter = Filters.eq(MongoConstants.Id, QueryHelper.createID(input.getProjectID()));

            result = mongoTemplate.getCollection(MongoConstants.Projects)
                    .deleteOne(projectFilter);
        } catch (MongoException e) {
            result = DeleteResult.unacknowledged();
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
                    if (propertyInfo.getPropertyName().equals(MongoConstants.SprintID)) {
                        String property = QueryHelper.asInnerDocumentArrayProperty(MongoConstants.Tasks, MongoConstants.Sprint);
                        updatePredicates.add(Updates.set(property, QueryHelper.createID(propertyInfo.getPropertyValue().toString())));
                    } else {
                        String property = QueryHelper.asInnerDocumentArrayProperty(MongoConstants.Tasks, propertyInfo.getPropertyName());
                        updatePredicates.add(Updates.set(property, propertyInfo.getPropertyValue()));
                    }

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
    public UpdateResult deleteTask(ProjectDeleteTaskInput input) {
        UpdateResult result;
        try {
            Bson projectFilter = Filters.eq(MongoConstants.Id, QueryHelper.createID(input.getProjectID()));

            Bson update = Updates.pull(MongoConstants.Tasks, new Document(MongoConstants.Id, QueryHelper.createID(input.getTaskID())));

            result = mongoTemplate.getCollection(MongoConstants.Projects)
                    .updateOne(projectFilter, update);
        } catch (MongoException e) {
            result = UpdateResult.unacknowledged();
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
    public UpdateResult removeCommentFromTask(TaskDeleteCommentInput input) {
        UpdateResult result;
        try {
            Bson projectFilter = Filters.eq(MongoConstants.Id, QueryHelper.createID(input.getProjectID()));
            Bson taskFilter = Filters.eq(QueryHelper.asInnerDocumentProperty(MongoConstants.Tasks, MongoConstants.Id), QueryHelper.createID(input.getTaskID()));

            String fieldName = QueryHelper.asInnerDocumentArrayProperty(MongoConstants.Tasks, MongoConstants.Comments, "");

            Bson update = Updates.pull(fieldName, new Document(MongoConstants.Id, QueryHelper.createID(input.getCommentID())));

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

    @Override
    public UpdateResult removeAssigneeFromTask(TaskDeleteAssigneeInput input) {
        UpdateResult result;
        try {
            Bson projectFilter = Filters.eq(MongoConstants.Id, QueryHelper.createID(input.getProjectID()));
            Bson taskFilter = Filters.eq(QueryHelper.asInnerDocumentProperty(MongoConstants.Tasks, MongoConstants.Id), QueryHelper.createID(input.getTaskID()));

            String fieldName = QueryHelper.asInnerDocumentArrayProperty(MongoConstants.Tasks, MongoConstants.Assignees, "");

            Bson update = Updates.pull(fieldName, new Document(MongoConstants.Id, QueryHelper.createID(input.getAssigneeID())));

            result = mongoTemplate.getCollection(MongoConstants.Projects)
                    .updateOne(Filters.and(projectFilter, taskFilter), update);
        } catch (MongoException e) {
            result = UpdateResult.unacknowledged();
        }
        return result;
    }

    @Override
    public UpdateResult addLabelToTask(TaskAddLabelInput input) {
        UpdateResult result;
        try {
            Bson projectFilter = Filters.eq(MongoConstants.Id, QueryHelper.createID(input.getProjectID()));
            Bson taskFilter = Filters.eq(QueryHelper.asInnerDocumentProperty(MongoConstants.Tasks, MongoConstants.Id), QueryHelper.createID(input.getTaskID()));

            String assigneesName = QueryHelper.asInnerDocumentArrayProperty(MongoConstants.Tasks, MongoConstants.Labels);

            Bson update = Updates.push(assigneesName, input.toDocument());

            result = mongoTemplate.getCollection(MongoConstants.Projects)
                    .updateOne(Filters.and(projectFilter, taskFilter), update);
        } catch (MongoException e) {
            result = UpdateResult.unacknowledged();
        }
        return result;
    }

    @Override
    public UpdateResult removeLabelFromTask(TaskDeleteLabelInput input) {
        UpdateResult result;
        try {
            Bson projectFilter = Filters.eq(MongoConstants.Id, QueryHelper.createID(input.getProjectID()));
            Bson taskFilter = Filters.eq(QueryHelper.asInnerDocumentProperty(MongoConstants.Tasks, MongoConstants.Id), QueryHelper.createID(input.getTaskID()));

            String fieldName = QueryHelper.asInnerDocumentArrayProperty(MongoConstants.Tasks, MongoConstants.Labels, "");

            Bson update = Updates.pull(fieldName, new Document(MongoConstants.Id, QueryHelper.createID(input.getLabelID())));

            result = mongoTemplate.getCollection(MongoConstants.Projects)
                    .updateOne(Filters.and(projectFilter, taskFilter), update);
        } catch (MongoException e) {
            result = UpdateResult.unacknowledged();
        }
        return result;
    }

    @Override
    public UpdateResult addSprint(SprintCreateInput input) {
        UpdateResult result;
        try {
            Bson projectFilter = Filters.eq(MongoConstants.Id, QueryHelper.createID((input.getProjectID())));

            Bson update = Updates.push(MongoConstants.Sprint, input.toDocument());

            result = mongoTemplate.getCollection(MongoConstants.Projects)
                    .updateOne(projectFilter, update);
        } catch (MongoException e) {
            result = UpdateResult.unacknowledged();
        }
        return result;
    }

    @Override
    public Document updateSprint(String projectID, String sprintID, ArrayList<PropertyInfo<?>> propertyInfoList) {
        Document result;

        ArrayList<Bson> updateOperations = new ArrayList<>();

        for (PropertyInfo<?> propertyInfo : propertyInfoList) {
            if (propertyInfo.isString() || propertyInfo.isCollection() || propertyInfo.isDate() || propertyInfo.isBoolean()) {
                if (!propertyInfo.getPropertyName().equals("projectID")) {

                    String propertyName = QueryHelper.asInnerDocumentArrayProperty(MongoConstants.Sprint, propertyInfo.getPropertyName());
                    updateOperations.add(Updates.set(propertyName, propertyInfo.getPropertyValue()));
                }
            }
        }

        if (updateOperations.size() == 0) {
            return new Document(ErrorMessages.Title, ErrorMessages.NoPropertyToUpdateError(updateOperations.size()));
        }

        try {
            Bson projectFilter = Filters.eq(MongoConstants.Id, QueryHelper.createID(projectID));
            Bson sprintFilter = Filters.eq(
                    QueryHelper.asInnerDocumentProperty(MongoConstants.Sprint, MongoConstants.Id), QueryHelper.createID(sprintID));


            result = mongoTemplate.getCollection(MongoConstants.Projects)
                    .findOneAndUpdate(Filters.and(projectFilter, sprintFilter),
                            Updates.combine(updateOperations));
        } catch (MongoException e) {
            result = new Document(ErrorMessages.Title, e.getMessage());
        }
        return result;
    }

    @Override
    public UpdateResult deleteSprint(SprintDeleteInput input) {
        UpdateResult result;
        try {
            Bson projectFilter = Filters.eq(MongoConstants.Id, QueryHelper.createID(input.getProjectID()));

            Bson update = Updates.pull(MongoConstants.Sprint, new Document(MongoConstants.Id, QueryHelper.createID(input.getSprintID())));

            result = mongoTemplate.getCollection(MongoConstants.Projects)
                    .updateOne(projectFilter, update);

            if (result.getModifiedCount() > 0) {
                this.clearSprintFromTask(input);
            }
        } catch (MongoException e) {
            result = UpdateResult.unacknowledged();
        }
        return result;
    }

    @Override
    public UpdateResult addManager(ProjectAddManagerInput input) {
        UpdateResult result;
        try {
            Bson projectFilter = Filters.eq(MongoConstants.Id, QueryHelper.createID(input.getProjectID()));
            Bson teamMemberFilter = Filters.eq(
                    QueryHelper.asInnerDocumentProperty(MongoConstants.TeamMembers, MongoConstants.Id),
                    QueryHelper.createID(input.getUserID()));

            Bson update = Updates.set(
                    QueryHelper.asInnerDocumentArrayProperty(MongoConstants.TeamMembers, MongoConstants.ProjectRole),
                    input.getProjectRole());

            result = mongoTemplate.getCollection(MongoConstants.Projects)
                    .updateOne(Filters.and(projectFilter, teamMemberFilter), update);

        } catch (MongoException e) {
            result = UpdateResult.unacknowledged();
        }
        return result;
    }

    @Override
    public UpdateResult assignTaskToSprint(TaskSprintAssignInput input) {
        UpdateResult result;
        try {
            Bson projectFilter = Filters.eq(MongoConstants.Id, QueryHelper.createID(input.getProjectID()));
            Bson taskFilter = Filters.eq(
                    QueryHelper.asInnerDocumentProperty(MongoConstants.Tasks, MongoConstants.Id),
                    QueryHelper.createID(input.getTaskID()));


            Bson update;
            if (input.getSprintID().equals("")) {
                update = Updates.set(
                        QueryHelper.asInnerDocumentArrayProperty(MongoConstants.Tasks, MongoConstants.SprintID),
                        BsonNull.VALUE);
            } else {
                update = Updates.set(
                        QueryHelper.asInnerDocumentArrayProperty(MongoConstants.Tasks, MongoConstants.SprintID),
                        QueryHelper.createID(input.getSprintID()));
            }

            result = mongoTemplate.getCollection(MongoConstants.Projects)
                    .updateOne(Filters.and(projectFilter, taskFilter), update);
        } catch (MongoException e) {
            result = UpdateResult.unacknowledged();
        }
        return result;
    }

    public UpdateResult clearSprintFromTask(SprintDeleteInput input) {
        UpdateResult result;
        try {
            Bson projectFilter = Filters.eq(MongoConstants.Id, QueryHelper.createID(input.getProjectID()));
            Bson taskWithSprint = Filters.eq(
                    QueryHelper.asInnerDocumentProperty(MongoConstants.Tasks, MongoConstants.SprintID),
                    QueryHelper.createID(input.getSprintID()));

            Bson update = Updates.set(
                    QueryHelper.asInnerDocumentArrayProperty(MongoConstants.Tasks, MongoConstants.SprintID),
                    BsonNull.VALUE);

            result = mongoTemplate.getCollection(MongoConstants.Projects)
                    .updateMany(Filters.and(projectFilter, taskWithSprint), update);
        } catch (MongoException e) {
            result = UpdateResult.unacknowledged();
        }
        return result;
    }

    @Override
    public UpdateResult setActiveSprint(SprintActiveInput input) {
        UpdateResult result;

        this.cleanActiveSprints(input);
        try {
            Bson projectFilter = Filters.eq(MongoConstants.Id, QueryHelper.createID(input.getProjectID()));
            Bson sprintFilter = Filters.eq(QueryHelper.asInnerDocumentProperty(MongoConstants.Sprint, MongoConstants.Id),
                    QueryHelper.createID(input.getSprintID()));

            Bson update = Updates.set(
                    QueryHelper.asInnerDocumentArrayProperty(MongoConstants.Sprint, MongoConstants.Active),
                    true);

            Bson openIfClosed = Updates.set(
                    QueryHelper.asInnerDocumentArrayProperty(MongoConstants.Sprint, MongoConstants.IsClosed),
                    false);

            result = mongoTemplate.getCollection(MongoConstants.Projects)
                    .updateOne(Filters.and(projectFilter, sprintFilter), Updates.combine(update, openIfClosed));
        } catch (MongoException e) {
            result = UpdateResult.unacknowledged();
        }
        return result;
    }

    public UpdateResult cleanActiveSprints(SprintActiveInput input) {
        UpdateResult result;
        try {
            Bson projectFilter = Filters.eq(MongoConstants.Id, QueryHelper.createID(input.getProjectID()));
            Bson activeSprintFilter = Filters.eq(
                    QueryHelper.asInnerDocumentProperty(MongoConstants.Sprint, MongoConstants.Active), true);

            Bson closeActiveUpdate = Updates.set(
                    QueryHelper.asInnerDocumentArrayProperty(MongoConstants.Sprint, MongoConstants.Active), false);

            Bson setIsClosedUpdate = Updates.set(
                    QueryHelper.asInnerDocumentArrayProperty(MongoConstants.Sprint, MongoConstants.IsClosed), true);

            result = mongoTemplate.getCollection(MongoConstants.Projects)
                    .updateMany(Filters.and(projectFilter, activeSprintFilter),
                            Updates.combine(closeActiveUpdate, setIsClosedUpdate));
        } catch (MongoException e) {
            result = UpdateResult.unacknowledged();
        }
        return result;
    }
}

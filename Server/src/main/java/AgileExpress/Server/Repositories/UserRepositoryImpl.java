package AgileExpress.Server.Repositories;

import AgileExpress.Server.Constants.MongoConstants;
import AgileExpress.Server.Entities.Project;
import AgileExpress.Server.Entities.UserProjects;
import AgileExpress.Server.Helpers.QueryHelper;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.TextSearchOptions;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepositoryCustom {


    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public Optional<List<Project>> getUserProjets(String query) {
        Optional<List<Project>> optionalProjects;
        try {
            ArrayList<Bson> stages = new ArrayList<>();

            TextSearchOptions options = new TextSearchOptions().caseSensitive(false).language("tr");
            Bson matchStage = Aggregates.match(Filters.text(query, options));
            Bson lookupStage = Aggregates.lookup(
                    MongoConstants.Projects,
                    MongoConstants.Id,
                    QueryHelper.asInnerDocumentProperty(MongoConstants.TeamMembers, MongoConstants.Id),
                    MongoConstants.UserProjects);

            Bson projectionStage = Aggregates.project(Projections.fields(Projections.include(MongoConstants.UserProjects), Projections.exclude(MongoConstants.Id)));

            stages.add(matchStage);
            stages.add(lookupStage);
            stages.add(projectionStage);


            AggregateIterable<Document> aggregateResult = mongoTemplate.getCollection(MongoConstants.Users)
                    .aggregate(stages);

            MongoConverter converter = mongoTemplate.getConverter();

            Document document = aggregateResult.first();

            if(document != null) {
                document.put(MongoConstants.Class, UserProjects.class);

                UserProjects userProjects = converter.read(UserProjects.class, document);

                optionalProjects = Optional.of(userProjects.getUserProjects());
            } else {
                optionalProjects = Optional.empty();
            }
        } catch (MongoException e) {
            optionalProjects = Optional.empty();
        }
        return optionalProjects;
    }
}

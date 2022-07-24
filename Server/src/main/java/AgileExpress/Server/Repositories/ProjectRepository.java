package AgileExpress.Server.Repositories;

import AgileExpress.Server.Entities.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, String> {

}

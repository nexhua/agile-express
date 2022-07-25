package AgileExpress.Server.Repositories;

import AgileExpress.Server.Entities.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends MongoRepository<Project, String>, ProjectRepositoryCustom {

    @Query("{ 'id' : ?0 }")
    Optional<Project> findById(String id);

    @Override
    List<Project> findAll();

    @Override
    Project insert(Project project);
}

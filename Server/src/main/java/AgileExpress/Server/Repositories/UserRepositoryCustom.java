package AgileExpress.Server.Repositories;

import AgileExpress.Server.Entities.Project;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryCustom {

    Optional<List<Project>> getUserProjets(String query);
}

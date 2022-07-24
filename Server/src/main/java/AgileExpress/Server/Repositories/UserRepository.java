package AgileExpress.Server.Repositories;

import AgileExpress.Server.Entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}

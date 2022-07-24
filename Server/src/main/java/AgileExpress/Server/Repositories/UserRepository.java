package AgileExpress.Server.Repositories;

import AgileExpress.Server.Entities.User;
import lombok.Builder;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);
}

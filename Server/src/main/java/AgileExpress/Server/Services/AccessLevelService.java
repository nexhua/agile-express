package AgileExpress.Server.Services;

import AgileExpress.Server.Constants.UserTypes;
import AgileExpress.Server.Entities.User;
import AgileExpress.Server.Repositories.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class AccessLevelService {

    private final UserRepository repository;

    public AccessLevelService(UserRepository repository) {
        this.repository = repository;
    }

    @Cacheable("UserAccessLevelList")
    public UserTypes getUserType(String username) {
        User user = this.repository.findByUsername(username);
        return user != null ? user.getType() : UserTypes.TEAM_MEMBER;
    }
}

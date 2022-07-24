package AgileExpress.Server.Helpers;

import AgileExpress.Server.Constants.UserTypes;
import AgileExpress.Server.Entities.User;
import AgileExpress.Server.Repositories.UserRepository;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class AccessLevelHelper implements ApplicationContextAware {

    private static ApplicationContext context;

    private final UserRepository repository;

    public AccessLevelHelper(UserRepository repository) {
        this.repository = repository;
    }

    public UserTypes getAccessLevel() {
        User user = this.repository.findByUsername(AuthHelper.getUsername());
        return user.getType();
    }

    public static <T extends Object> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AccessLevelHelper.context = context;
    }
}

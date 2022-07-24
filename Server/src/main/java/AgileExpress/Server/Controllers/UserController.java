package AgileExpress.Server.Controllers;

import AgileExpress.Server.Constants.ApiRouteConstants;
import AgileExpress.Server.Entities.User;
import AgileExpress.Server.Repositories.UserRepository;
import com.mongodb.MongoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping(ApiRouteConstants.GetUsers)
    public ResponseEntity<?> getUsers() {
        boolean querySuccess = false;
        List<User> userList = Collections.emptyList();

        try {
            userList = repository.findAll();
            querySuccess = true;
        } catch (MongoException e) {
            System.out.println(e.getMessage());
        }

        if (querySuccess) {
            return new ResponseEntity<>(userList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(ApiRouteConstants.GetUser)
    public ResponseEntity<?> getUser(@PathVariable String username) {
        return new ResponseEntity<>(repository.findByUsername(username),HttpStatus.OK);
    }
}

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
        List<User> userList;
        ResponseEntity response;
        try {
            userList = repository.findAll();
            response = new ResponseEntity<>(userList, HttpStatus.OK);
        } catch (MongoException e) {
            System.out.println(e.getMessage());
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @GetMapping(ApiRouteConstants.GetUser)
    public ResponseEntity<?> getUser(@PathVariable String username) {
        User user = null;
        ResponseEntity response;
        try {
            user = repository.findByUsername(username);
            if(user != null) {
                response = new ResponseEntity(user, HttpStatus.OK);
            } else {
                response = new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}

package AgileExpress.Server.Controllers;

import AgileExpress.Server.Constants.ApiRouteConstants;
import AgileExpress.Server.Constants.ErrorMessages;
import AgileExpress.Server.Constants.UserTypes;
import AgileExpress.Server.Entities.User;
import AgileExpress.Server.Entities.UserContext;
import AgileExpress.Server.Helpers.AccessLevelHelper;
import AgileExpress.Server.Helpers.AuthHelper;
import AgileExpress.Server.Repositories.UserRepository;
import AgileExpress.Server.Services.AccessLevelService;
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

    private final AccessLevelService service;

    public UserController(UserRepository repository, AccessLevelService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping(ApiRouteConstants.GetUsers)
    public ResponseEntity<?> getUsers() {
        UserContext context = this.service.getUserType(AuthHelper.getUsername());

        if (context == null) {
            return new ResponseEntity<>(ErrorMessages.with(ErrorMessages.UserNotFoundError()), HttpStatus.NOT_FOUND);
        }

        UserTypes userTypes = context.getType();
        if(AccessLevelHelper.hasHigherOrEqualAccessLevel(userTypes, UserTypes.ADMIN)){
            ResponseEntity response;
            List<User> userList;
            try {
                userList = repository.findAll();
                response = new ResponseEntity<>(userList, HttpStatus.OK);
            } catch (MongoException e) {
                System.out.println(e.getMessage());
                response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return response;
        }
        else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(ApiRouteConstants.GetUser)
    public ResponseEntity<?> getUser(@PathVariable String username) {
        UserContext context = this.service.getUserType(AuthHelper.getUsername());

        if(context == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }else {
            if(AccessLevelHelper.hasHigherOrEqualAccessLevel(context.getType(), UserTypes.ADMIN)) {
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
            else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
    }
}

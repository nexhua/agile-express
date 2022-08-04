package AgileExpress.Server.Controllers;

import AgileExpress.Server.Constants.ApiRouteConstants;
import AgileExpress.Server.Constants.ErrorMessages;
import AgileExpress.Server.Entities.User;
import AgileExpress.Server.Entities.UserContext;
import AgileExpress.Server.Helpers.AuthHelper;
import AgileExpress.Server.Inputs.User.SignUpInput;
import AgileExpress.Server.LDAP.LDIFUser;
import AgileExpress.Server.Repositories.UserRepository;
import AgileExpress.Server.Services.AccessLevelService;
import com.mongodb.MongoException;
import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class AuthController {

    private final UserRepository repository;

    private final AccessLevelService service;

    public AuthController(UserRepository repository, AccessLevelService service) {
        this.repository = repository;
        this.service = service;
    }

    @PostMapping(ApiRouteConstants.SignUp)
    public ResponseEntity SignUp(@RequestBody SignUpInput input) throws URISyntaxException {
        User user = new User(input.getUserName(), input.getEmail(), input.getName(), input.getSurname(), input.getType());

        ResponseEntity response;
        try {
            repository.save(user);
            LDIFUser ldifUser = new LDIFUser(input.getUserName(), input.getPasswordHash(), input.getOrganization(), input.getName(), input.getSurname());
            ldifUser.save();
            response = new ResponseEntity(HttpStatus.CREATED);
        } catch (MongoException e) {
            System.out.println(e.getMessage());
            response = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @GetMapping(ApiRouteConstants.GetUsername)
    public ResponseEntity<?> currentUserName() {
        if (AuthHelper.isAuthenticated()) {
            Map<String, Object> object = new LinkedHashMap<>();
            object.put("username", AuthHelper.getUsername());

            return ResponseEntity.ok().body(object);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(ApiRouteConstants.GetAccessLevel)
    public ResponseEntity<?> getAccessLevel() {
        UserContext context = this.service.getUserType(AuthHelper.getUsername());

        if (context == null) {
            return new ResponseEntity<>(ErrorMessages.with(ErrorMessages.UserNotFoundError()), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(
                    new Document("accessLevel", context.getType().ordinal()), HttpStatus.OK);
        }
    }

    @GetMapping(ApiRouteConstants.GetAuthenticatedUser)
    public ResponseEntity<?> getUser() {
        ResponseEntity response;
        if (AuthHelper.isAuthenticated()) {
            String username = AuthHelper.getUsername();

            User user = this.repository.findByUsername(username);
            if (user != null) {
                response = new ResponseEntity(user, HttpStatus.OK);
            }
            else {
                response = new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            response = new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        return response;
    }
}

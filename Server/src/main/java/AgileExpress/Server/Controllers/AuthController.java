package AgileExpress.Server.Controllers;

import AgileExpress.Server.Constants.ApiRouteConstants;
import AgileExpress.Server.Entities.User;
import AgileExpress.Server.Inputs.SignUpInput;
import AgileExpress.Server.LDAP.LDIFUser;
import AgileExpress.Server.Repositories.UserRepository;
import com.mongodb.MongoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
public class AuthController {

    private final UserRepository repository;

    public AuthController(UserRepository repository) {
        this.repository = repository;
    }
    @PostMapping(ApiRouteConstants.SignUp)
    public ResponseEntity SignUp(@RequestBody SignUpInput input) throws URISyntaxException
    {
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
}

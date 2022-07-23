package AgileExpress.Server.Controllers;

import AgileExpress.Server.Constants.ApiRouteConstants;
import AgileExpress.Server.Inputs.SignUpInput;
import AgileExpress.Server.LDAP.LDIFUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
public class AuthController {


    @PostMapping(ApiRouteConstants.SignUp)
    public ResponseEntity SignUp(@RequestBody SignUpInput input) throws URISyntaxException
    {
        LDIFUser user = new LDIFUser(input.getUserName(), input.getPasswordHash(), input.getOrganization(), input.getName(), input.getSurname());

        user.save();

        return new ResponseEntity(HttpStatus.OK);
    }
}

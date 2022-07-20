package AgileExpress.Server.Controllers;

import AgileExpress.Server.Constants.ApiRouteConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @PostMapping(ApiRouteConstants.SignIn)
    public ResponseEntity SignIn()
    {
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(ApiRouteConstants.SignOut)
    public ResponseEntity SignOut()
    {
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(ApiRouteConstants.SignUp)
    public ResponseEntity SignUp()
    {
        return new ResponseEntity(HttpStatus.OK);
    }
}

package AgileExpress.Server.Controllers;

import AgileExpress.Server.Entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class SecurityController {

    @GetMapping("/username")
    public ResponseEntity<User> currentUserName(Authentication auth) {
        if(auth.isAuthenticated())
        {
            return ResponseEntity.ok().body(new User(auth.getName()));
        }
        return ResponseEntity.notFound().build();
    }
}

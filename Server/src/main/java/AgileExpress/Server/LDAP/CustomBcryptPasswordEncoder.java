package AgileExpress.Server.LDAP;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CustomBcryptPasswordEncoder extends BCryptPasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return "{bcrypt}" + super.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if(encodedPassword.length() > 60)
        {
            return super.matches(rawPassword, encodedPassword.substring(8));
        }
        else {
            return super.matches(rawPassword, encodedPassword);
        }

    }
}

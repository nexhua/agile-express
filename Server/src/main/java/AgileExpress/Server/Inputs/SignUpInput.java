package AgileExpress.Server.Inputs;

import AgileExpress.Server.Constants.UserTypes;
import AgileExpress.Server.LDAP.CustomBcryptPasswordEncoder;
import org.hibernate.usertype.UserType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SignUpInput {

    private String name;
    private String surname;
    private String email;
    private String userName;
    private String passwordHash;
    private String organization;
    private UserTypes type = UserTypes.TEAM_MEMBER;

    public SignUpInput(String name, String surname, String email, String userName, String password, String organization, UserTypes type)
    {
        BCryptPasswordEncoder encoder = new CustomBcryptPasswordEncoder();

        this.name = name;
        this.surname = surname;
        this.email = email;
        this.userName = userName;
        this.passwordHash = encoder.encode(password);
        this.organization = organization;
        this.type = type;
    }

    //region Getter and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public UserTypes getType() {
        return type;
    }

    public void setType(UserTypes type) {
        this.type = type;
    }

    //endregion
}

package datasec.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Michał on 2017-06-01.
 */

/**
 * Holds which user should be logged
 */
public class UserToLogin {

    @NotNull
    @Size(min = 5, max = 8)
    @Pattern(regexp = "([a-z])+")
    private String login;

    @NotNull
    @Size(min = 8)
    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})")
    private String password;

    public UserToLogin() {
    }

    public UserToLogin(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

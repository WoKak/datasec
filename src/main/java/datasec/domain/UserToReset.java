package datasec.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Michał on 2017-06-03.
 */

/**
 * Holds information about login of user to reset
 */
public class UserToReset {

    @NotNull
    @Size(min = 5, max = 8)
    @Pattern(regexp = "([a-z])+")
    private String login;

    public UserToReset(String login) {
        this.login = login;
    }

    public UserToReset() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}

package datasec.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Michał on 2017-06-02.
 */

/**
 * Object used for password change
 */
public class PasswordToChange {

    @NotNull
    @Size(min = 8)
    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})")
    private String old;

    @NotNull
    @Size(min = 8)
    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})")
    private String password;

    @NotNull
    @Size(min = 8)
    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})")
    private String repeated;

    public PasswordToChange() {
    }

    public PasswordToChange(String old, String password, String repeated) {
        this.old = old;
        this.password = password;
        this.repeated = repeated;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeated() {
        return repeated;
    }

    public void setRepeated(String repeated) {
        this.repeated = repeated;
    }

    public String getOld() {

        return old;
    }

    public void setOld(String old) {
        this.old = old;
    }
}

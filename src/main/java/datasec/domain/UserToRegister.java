package datasec.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Michał on 2017-06-01.
 */
public class UserToRegister {

    @NotNull
    @Size(min = 5, max = 8)
    @Pattern(regexp = "([a-z])+")
    private String login;

    @NotNull
    @Size(min = 8)
    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})")
    private String password;

    @NotNull
    @Size(min = 8)
    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})")
    private String repeatedPassword;

    @NotNull
    private String question;

    @NotNull
    private String answer;

    public UserToRegister() {
    }

    public UserToRegister(String login, String password, String repeatedPassword, String question, String answer) {
        this.login = login;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
        this.question = question;
        this.answer = answer;
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

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

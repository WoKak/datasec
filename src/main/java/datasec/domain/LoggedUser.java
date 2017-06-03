package datasec.domain;

/**
 * Created by Michał on 2017-06-01.
 */

public class LoggedUser {

    private String login;
    private boolean logged;

    public LoggedUser(String login) {
        this.login = login;
        this.logged = false;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }
}

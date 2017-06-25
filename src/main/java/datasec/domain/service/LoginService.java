package datasec.domain.service;

import com.google.common.hash.Hashing;
import datasec.domain.LoggedUser;
import datasec.domain.UserToLogin;
import datasec.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Created by Michał on 2017-06-01.
 */

@Service
public class LoginService {

    private DataSource dataSource;
    private LoggedUser loggedUser;

    @Autowired
    public LoginService(DataSource ds, LoggedUser lu) {

        this.dataSource = ds;
        this.loggedUser = lu;
    }

    /**
     * method used for authorization - in typical way
     */
    public void auth(UserToLogin newUserToLogin, BindingResult bindingResult) {

        try {

            if (Optional.ofNullable(bindingResult).isPresent())
                if (bindingResult.hasErrors()) {

                    throw new ApplicationException("Błąd aplikacji!");
                }

            SlowThread t = new SlowThread();

            t.run();

            Connection connection = dataSource.getConnection();

            String query = "SELECT login, password FROM users WHERE login=?";
            PreparedStatement checkStat = connection.prepareStatement(query);
            checkStat.setString(1, newUserToLogin.getLogin());
            ResultSet result = checkStat.executeQuery();

            t.run();

            if (!result.next()) {
                throw new ApplicationException("Błąd aplikacji!");
            }

            String salted = "^*)" + newUserToLogin.getPassword() + "%h&";

            String hashToCheck = Hashing.sha256().hashString(salted, StandardCharsets.UTF_8).toString();

            //thousand times hashed
            for(int i = 0; i < 1000; i++) {
                hashToCheck = Hashing.sha256().hashString(hashToCheck, StandardCharsets.UTF_8).toString();
            }

            if (!hashToCheck.equals(result.getString(2))) {
                throw new ApplicationException("Błąd aplikacji!");
            }

            t.run();

            loggedUser.setLogged(true);
            loggedUser.setLogin(newUserToLogin.getLogin());

        } catch (SQLException ex) {

            ex.printStackTrace();
            throw new ApplicationException("Błąd aplikacji!");
        }
    }
}

/**
 * thread used for slowing whole process
 */
class SlowThread implements Runnable {

    @Override
    public void run() {

        try {

            Thread.sleep(2000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

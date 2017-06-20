package datasec.domain.service;

import com.google.common.hash.Hashing;
import datasec.domain.LoggedUser;
import datasec.domain.PasswordToChange;
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
 * Created by Michał on 2017-06-02.
 */

@Service
public class PasswordChangeService {

    @Autowired
    DataSource dataSource;

    @Autowired
    LoggedUser loggedUser;

    /**
     * Changes user password, checks if previous matches, then change
     * @param passwordToChange object containing required information
     * @param bindingResult result of ninding
     */
    public void change(PasswordToChange passwordToChange, BindingResult bindingResult) {

        try {

            if (Optional.ofNullable(bindingResult).isPresent())
                if (bindingResult.hasErrors()) {
                    throw new ApplicationException("Błąd aplikacji!");
                }

            Connection connection = dataSource.getConnection();


            String query = "SELECT * FROM users WHERE login=?";
            PreparedStatement checkStat = connection.prepareStatement(query);
            checkStat.setString(1, loggedUser.getLogin());
            ResultSet result = checkStat.executeQuery();

            String salted = "^*)" + passwordToChange.getOld() + "%h&";

            String hash = Hashing.sha256().hashString(salted, StandardCharsets.UTF_8).toString();

            for(int i = 0; i < 1000; i++) {
                hash = Hashing.sha256().hashString(hash, StandardCharsets.UTF_8).toString();
            }

            result.next();

            if (!hash.equals(result.getString(2))) {
                throw new ApplicationException("Błąd aplikacji!");
            }

            if (!passwordToChange.getPassword().equals(passwordToChange.getRepeated())) {
                throw new ApplicationException("Błąd aplikacji!");
            }


            String saltedNew = "^*)" + passwordToChange.getPassword() + "%h&";

            String hashNew = Hashing.sha256().hashString(saltedNew, StandardCharsets.UTF_8).toString();

            for(int i = 0; i < 1000; i++) {
                hashNew = Hashing.sha256().hashString(hashNew, StandardCharsets.UTF_8).toString();
            }

            String updateQuery = "UPDATE users SET password = ? WHERE login = ?";
            PreparedStatement pstat = connection.prepareStatement(updateQuery);
            pstat.setString(1, hashNew);
            pstat.setString(2, loggedUser.getLogin());
            pstat.executeUpdate();

        } catch (SQLException ex) {

            ex.printStackTrace();
            throw new ApplicationException("Błąd aplikacji!");
        }
    }
}

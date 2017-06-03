package datasec.domain.service;

import com.google.common.hash.Hashing;
import datasec.domain.UserToRegister;
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
public class UserToRegisterService {

    private DataSource dataSource;

    @Autowired
    public UserToRegisterService(DataSource ds) {
        this.dataSource = ds;
    }

    public void addUser(UserToRegister newUserToRegister, BindingResult bindingResult) {

        try {

            if (Optional.ofNullable(bindingResult).isPresent())
                if (bindingResult.hasErrors()) {
                    throw new ApplicationException("Błąd aplikacji!");
                }

            Connection connection = dataSource.getConnection();


            String query = "SELECT * FROM users WHERE login=?";
            PreparedStatement checkStat = connection.prepareStatement(query);
            checkStat.setString(1, newUserToRegister.getLogin());
            ResultSet result = checkStat.executeQuery();

            //checks whether user exists
            if (result.next()) {
                throw new ApplicationException("Błąd aplikacji!");
            }

            //password equals repeated password
            if (!newUserToRegister.getPassword().equals(newUserToRegister.getRepeatedPassword())) {
                throw new ApplicationException("Błąd aplikacji!");
            }

            String insert = "INSERT INTO users (login, password) VALUES (?, ?)";


            String salted = "^*)" + newUserToRegister.getPassword() + "%h&";

            String hash = Hashing.sha256().hashString(salted, StandardCharsets.UTF_8).toString();

            for(int i = 0; i < 5; i++) {
                hash = Hashing.sha256().hashString(hash, StandardCharsets.UTF_8).toString();
            }

            PreparedStatement pstat = connection.prepareStatement(insert);
            pstat.setString(1, newUserToRegister.getLogin());
            pstat.setString(2, hash);
            pstat.executeUpdate();

        } catch (SQLException ex) {

            ex.printStackTrace();
            throw new ApplicationException("Błąd aplikacji!");
        }
    }
}

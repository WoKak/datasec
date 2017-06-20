package datasec.domain.service;

import com.google.common.hash.Hashing;
import datasec.domain.LoggedUser;
import datasec.domain.NewQuestion;
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
 * Created by Michał on 2017-06-03.
 */

@Service
public class QuestionService {

    @Autowired
    DataSource dataSource;

    @Autowired
    LoggedUser loggedUser;

    /**
     * Changes question, checks previous, then change
     * @param q object containing required information
     * @param bindingResult result of binding the form
     */
    public void change(NewQuestion q, BindingResult bindingResult) {

        try {

            if (Optional.ofNullable(bindingResult).isPresent())
                if (bindingResult.hasErrors()) {
                    throw new ApplicationException("Błąd aplikacji!");
                }

            Connection connection = dataSource.getConnection();


            String query = "SELECT * FROM questions WHERE login=?";
            PreparedStatement checkStat = connection.prepareStatement(query);
            checkStat.setString(1, loggedUser.getLogin());
            ResultSet result = checkStat.executeQuery();

            result.next();

            String prevHash = Hashing.sha256().hashString(
                    q.getPrevious(), StandardCharsets.UTF_8
            ).toString();

            if (!result.getString(3).equals(prevHash)) {
                throw new ApplicationException("Błąd aplikacji!");
            }

            String nextHash = Hashing.sha256().hashString(
                    q.getAnswer(), StandardCharsets.UTF_8
            ).toString();

            String updateQuery = "UPDATE questions SET question = ?, answer = ? WHERE login = ?";
            PreparedStatement pstat = connection.prepareStatement(updateQuery);
            pstat.setString(1, q.getQuestion());
            pstat.setString(2, nextHash);
            pstat.setString(3, loggedUser.getLogin());
            pstat.executeUpdate();

        } catch (SQLException ex) {

            ex.printStackTrace();
            throw new ApplicationException("Błąd aplikacji!");
        }
    }
}

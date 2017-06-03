package datasec.domain.service;

import datasec.domain.LoggedUser;
import datasec.domain.NewQuestion;
import datasec.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.sql.DataSource;
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

            if (!result.getString(3).equals(q.getPrevious())) {
                throw new ApplicationException("Błąd aplikacji!");
            }

            String updateQuery = "UPDATE questions SET question = ?, answer = ? WHERE login = ?";
            PreparedStatement pstat = connection.prepareStatement(updateQuery);
            pstat.setString(1, q.getQuestion());
            pstat.setString(2, q.getAnswer());
            pstat.setString(3, loggedUser.getLogin());
            pstat.executeUpdate();

        } catch (SQLException ex) {

            ex.printStackTrace();
            throw new ApplicationException("Błąd aplikacji!");
        }
    }
}

package datasec.domain.service;

import com.google.common.hash.Hashing;
import datasec.domain.Answer;
import datasec.domain.LoggedUser;
import datasec.domain.Question;
import datasec.domain.UserToReset;
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
public class ResetService {

    @Autowired
    LoggedUser loggedUser;

    @Autowired
    DataSource dataSource;

    /**
     * Checks if user exists
     * @param userToReset (his/her login)
     */
    public void reset(UserToReset userToReset, BindingResult bindingResult) {

        if (Optional.ofNullable(bindingResult).isPresent())
            if (bindingResult.hasErrors()) {
                throw new ApplicationException("Błąd aplikacji!");
            }

        loggedUser.setLogin(userToReset.getLogin());
    }

    /**
     * method used for getting question from database, for user to reset
     * @return question for resetting password
     */
    public Question getQuestion() {

        Question toReturn;

        try {

            Connection connection = dataSource.getConnection();


            String query = "SELECT users.login, question, answer FROM users " +
                    "INNER JOIN questions ON users.login = questions.login " +
                    "WHERE questions.login = ? ";
            PreparedStatement checkStat = connection.prepareStatement(query);
            checkStat.setString(1, loggedUser.getLogin());
            ResultSet result = checkStat.executeQuery();

            result.next();

            toReturn = new Question(result.getString(2), result.getString(3));

        } catch (SQLException ex) {

            ex.printStackTrace();
            throw new ApplicationException("Błąd aplikacji!");
        }

        return toReturn;
    }

    /**
     * asserts answers
     * @param answer user answer
     * @param question question which had been asked
     */
    public void assertAnswers(Answer answer, Question question) {

        if ( check(answer, question) ) {

            loggedUser.setLogged(true);

        } else {

            throw new ApplicationException("Błąd aplikacji!");
        }
    }

    /**
     * Makes hash of answer and makes assertion, then returns boolean value
     */
    public boolean check(Answer answer, Question question) {

        String hash = Hashing.sha256().hashString(answer.getAnswer(), StandardCharsets.UTF_8).toString();

        return hash.equals(question.getAnswer());
    }
}

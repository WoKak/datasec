import datasec.domain.LoggedUser;
import datasec.domain.NewQuestion;
import datasec.domain.service.QuestionChangeService;
import datasec.exception.ApplicationException;
import org.junit.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.ScriptException;
import tools.Hash;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.springframework.jdbc.datasource.init.ScriptUtils.executeSqlScript;

/**
 * Created by Michał on 2017-07-02.
 */
public class QuestionChangeTest {

    private static EmbeddedDatabase database;
    private static LoggedUser loggedUser;
    private static QuestionChangeService questionChangeService;

    /**
     * Sets up database for tests
     */
    @BeforeClass
    public static void setUpDatabase() {
        database = new EmbeddedDatabaseBuilder()
                .setName("test")
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("database/db-schema.sql")
                .build();

        loggedUser = new LoggedUser("testUser");
        questionChangeService = new QuestionChangeService(database, loggedUser);
    }

    /**
     * Inserts data into test database
     * @throws ScriptException
     */
    @Before
    public void setUp() throws ScriptException {
        try {
            ClassPathResource resourceOne = new ClassPathResource("database/db-user.sql");
            executeSqlScript(database.getConnection(), new EncodedResource(resourceOne, "UTF-8"));

            ClassPathResource resourceTwo = new ClassPathResource("database/db-question.sql");
            executeSqlScript(database.getConnection(), new EncodedResource(resourceTwo, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(expected = ApplicationException.class)
    public void shouldNotAllowToChangeBecauseOfIncorrectAnswer() {

        NewQuestion test = new NewQuestion("Chelsea", "What?", "Nothing!");
        questionChangeService.change(test, null);
    }

    @Test
    public void shouldAllowToChange() {

        NewQuestion test = new NewQuestion("Chelski", "What?", "Nothing!");
        questionChangeService.change(test, null);

        try {

            Connection connection = database.getConnection();

            String query = "SELECT * FROM questions WHERE login = 'testUser'";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);

            result.next();

            String toAssert = Hash.helpfulHashMethod("Nothing!", 0, false);
            Assert.assertTrue(result.getNString(3).equals(toAssert));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDown() {
        database.shutdown();
    }
}

import datasec.domain.*;
import datasec.domain.service.LoginService;
import datasec.domain.service.ResetService;
import datasec.exception.ApplicationException;
import org.junit.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.ScriptException;
import tools.Hash;

import java.sql.SQLException;

import static org.springframework.jdbc.datasource.init.ScriptUtils.executeSqlScript;

/**
 * Created by Michał on 2017-06-25.
 */
public class ResetTest {

    private static EmbeddedDatabase database;
    private static ResetService resetService;
    private static LoggedUser loggedUser;

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

        loggedUser = new LoggedUser("");
        resetService = new ResetService(loggedUser, database);
    }

    /**
     * Inserts data into test database
     *
     * @throws ScriptException
     */
    @Before
    public void setUp() throws ScriptException {
        try {
            ClassPathResource resource = new ClassPathResource("database/db-user.sql");
            executeSqlScript(database.getConnection(), new EncodedResource(resource, "UTF-8"));

            ClassPathResource resourcePrime = new ClassPathResource("database/db-question.sql");
            executeSqlScript(database.getConnection(), new EncodedResource(resourcePrime, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldSetUserToReset() {

        UserToReset user = new UserToReset("testowy");
        resetService.reset(user, null);
    }

    @Test
    public void shouldReturnQuestion() throws SQLException {

        UserToReset user = new UserToReset("testUser");
        resetService.reset(user, null);

        Question test = new Question(
                "Who won the league?",
                Hash.helpfulHashService("Chelski", 0, false)
        );

        Question fromBase = resetService.getQuestion();

        Assert.assertTrue(test.equals(fromBase));
    }

    @Test(expected = SQLException.class)
    public void shouldNotReturnQuestion() throws SQLException {

        UserToReset user = new UserToReset("testUse");
        resetService.reset(user, null);

        Question fromBase = resetService.getQuestion();
    }

    @Test
    public void shouldAcceptAnswer() throws SQLException {

        UserToReset user = new UserToReset("testUser");
        resetService.reset(user, null);

        Question fromBase = resetService.getQuestion();
        Answer test = new Answer("Chelski");

        resetService.assertAnswers(test, fromBase);

        Assert.assertTrue(loggedUser.isLogged());
    }

    @Test(expected = ApplicationException.class)
    public void shouldNotAcceptAnswer() throws SQLException {

        UserToReset user = new UserToReset("testUser");
        resetService.reset(user, null);

        Question fromBase = resetService.getQuestion();
        Answer test = new Answer("Chelsea");

        resetService.assertAnswers(test, fromBase);
    }


    @AfterClass
    public static void tearDown() {
        database.shutdown();
    }
}

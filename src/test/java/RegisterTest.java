import datasec.domain.UserToRegister;
import datasec.domain.service.UserToRegisterService;
import datasec.exception.ApplicationException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.ScriptException;

import static org.springframework.jdbc.datasource.init.ScriptUtils.executeSqlScript;

/**
 * Created by Michał on 2017-07-01.
 */

//All requirements about passwords and login were omitted
public class RegisterTest {

    private static EmbeddedDatabase database;
    private static UserToRegisterService registerService;

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

        registerService = new UserToRegisterService(database);
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
    public void shouldNotAllowToRegisterBecauseOfSameLogin() {

        UserToRegister test = new UserToRegister(
                "testUser",
                "test",
                "test",
                "What?",
                "Nothing!"
        );

        registerService.addUser(test, null);
    }

    @Test(expected = ApplicationException.class)
    public void shouldNotAllowToRegisterBecauseOfDifferenceInPasswords() {

        UserToRegister test = new UserToRegister(
                "newUser",
                "testful",
                "test",
                "What?",
                "Nothing!"
        );

        registerService.addUser(test, null);
    }

    @Test
    public void shouldAllowToRegister() {

        UserToRegister test = new UserToRegister(
                "newUser",
                "test",
                "test",
                "What?",
                "Nothing!"
        );

        registerService.addUser(test, null);
    }

    @AfterClass
    public static void tearDown() {
        database.shutdown();
    }
}

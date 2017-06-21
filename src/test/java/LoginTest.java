import datasec.domain.LoggedUser;
import datasec.domain.UserToLogin;
import datasec.domain.service.UserToLoginService;
import datasec.exception.ApplicationException;
import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.ScriptException;

import static org.springframework.jdbc.datasource.init.ScriptUtils.executeSqlScript;

/**
 * Created by Michał on 2017-06-21.
 */
public class LoginTest {

    private static EmbeddedDatabase database;
    private static UserToLoginService userToLoginService;
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
        userToLoginService = new UserToLoginService(database, loggedUser);

    }

    /**
     * Inserts data into test database
     * @throws ScriptException
     */
    @Before
    public void setUp() throws ScriptException {
        try {
            ClassPathResource resource = new ClassPathResource("database/db-user.sql");
            executeSqlScript(database.getConnection(), new EncodedResource(resource, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(expected = ApplicationException.class)
    public void shouldNotAllowToLogin() {

        UserToLogin userToLogin = new UserToLogin("testUser", "Ol@mapsa1");
        userToLoginService.auth(userToLogin, null);
    }

    @Test()
    public void shouldLogin() {

        UserToLogin userToLogin = new UserToLogin("testUser", "Al@makota2");
        userToLoginService.auth(userToLogin, null);
        Assert.assertTrue(loggedUser.isLogged() && loggedUser.getLogin().equals("testUser"));
    }

    @AfterClass
    public static void tearDown() {
        database.shutdown();
    }
}

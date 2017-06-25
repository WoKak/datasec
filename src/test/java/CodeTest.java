import datasec.domain.Code;
import datasec.domain.repository.CodeRepository;
import datasec.domain.service.CodeService;
import org.junit.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.ScriptException;

import static org.springframework.jdbc.datasource.init.ScriptUtils.executeSqlScript;

/**
 * Created by Michał on 2017-06-25.
 */
public class CodeTest {

    private static EmbeddedDatabase database;
    private static CodeRepository codeRepo;
    private static CodeService codeService;

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

        codeRepo = new CodeRepository();
        codeService = new CodeService(codeRepo, database);
    }

    @Test
    public void shouldAddCodeToRepo() {

        Code code = new Code("int a = 5;");
        codeService.addCode(code, null);

        Assert.assertTrue(codeRepo.getSnippets().get(0).getText().trim().equals("int a = 5;"));
    }

    @Test
    public void shouldAddCodeToBase() {

        Code code = new Code("int a = 5;");

        CodeRepository test = new CodeRepository();
        CodeService tmp = new CodeService(test, database);
        tmp.addCode(code, null);

        Assert.assertTrue(test.getSnippets().get(0).getText().trim().equals("int a = 5;"));
    }

    @Test
    public void shouldInit() {

        insert();
        CodeRepository test = new CodeRepository();
        CodeService tmp = new CodeService(test, database);
        tmp.initRepo();

        Assert.assertTrue(test.getSnippets().size() == 4);
    }

    public static void insert() throws ScriptException {
        try {
            ClassPathResource resource = new ClassPathResource("database/db-code.sql");
            executeSqlScript(database.getConnection(), new EncodedResource(resource, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @AfterClass
    public static void tearDown() {
        database.shutdown();
    }
}

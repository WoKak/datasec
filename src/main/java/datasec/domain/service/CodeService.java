package datasec.domain.service;

import datasec.domain.Code;
import datasec.domain.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by Michał on 2017-05-25.
 */

@Service
public class CodeService {

    private CodeRepository codeRepo;
    private DataSource dataSource;

    @Autowired
    public CodeService(CodeRepository cr, DataSource ds) {
        this.codeRepo = cr;
        this.dataSource = ds;
    }

    /**
     * Adds snippet to database, as well as code repo
     * @param code to be added
     * @param result of binding
     */
    public void addCode(Code code, BindingResult result) {

        try {

            Connection conn = dataSource.getConnection();

            String insert = "INSERT INTO snippets (code) VALUES (?)";

            PreparedStatement pstat = conn.prepareStatement(insert);
            pstat.setString(1, code.getText());
            pstat.executeUpdate();


        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        codeRepo.getSnippets().add(code);
    }

    /**
     * method used for initializing the repo
     */
    public void initRepo() {

        try {

            Connection conn = dataSource.getConnection();

            Statement stat = conn.createStatement();

            ResultSet result = stat.executeQuery("SELECT * FROM snippets");

            while (result.next()) {

                Code tmp = new Code(result.getString(1).trim());
                codeRepo.getSnippets().add(tmp);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

package po.infrastructure.relational.config;

import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Log4j2
public class DatabaseUtils {

    /** Properties file for Staging database. */
    public final static String QA_PROPERTIES_FILENAME = "src/main/resources/database_staging.properties";

    /** Properties file for Unit testing database. */
    public final static String UNIT_PROPERTIES_FILENAME = "src/main/resources/database_unit.properties";

    public static DatabaseProperties qaProperties = new DatabaseProperties(QA_PROPERTIES_FILENAME);
    public static DatabaseProperties unitProperties = new DatabaseProperties(UNIT_PROPERTIES_FILENAME);

    public static Connection createConnection(DatabaseProperties prop) throws SQLException {
        String url = prop.getProperty(DatabaseProperties.URL);
        String user = prop.getProperty(DatabaseProperties.USER);
        String password = prop.getProperty(DatabaseProperties.PASSWORD);
        Connection connection = DriverManager.getConnection(url, user, password);
        log.info("Connection successfully created for db url: " + url);
        return connection;
    }

}

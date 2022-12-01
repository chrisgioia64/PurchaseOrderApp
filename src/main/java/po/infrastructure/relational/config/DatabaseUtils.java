package po.infrastructure.relational.config;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;
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

    public static DataSource createDataSource(DatabaseProperties prop) {
        return createDataSource(prop, false);
    }

    public static DataSource createDataSourceForDaoTesting(DatabaseProperties prop) {
        return createDataSource(prop, true);
    }

    private static DataSource createDataSource(DatabaseProperties prop, boolean autocommit) {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        // Wraps a single JDBC connection which is not closed after use
        SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(prop.getProperty(DatabaseProperties.URL));
        dataSource.setUsername(prop.getProperty(DatabaseProperties.USER));
        dataSource.setPassword(prop.getProperty(DatabaseProperties.PASSWORD));
        try {
            dataSource.getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.info("Created datasource for db url: " + prop.getProperty(DatabaseProperties.URL));
        return dataSource;
    }

}

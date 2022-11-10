package po.infrastructure.relational.config;

import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Log4j2
public class DatabaseUtils {

    public static Connection createConnection(DatabaseProperties prop) throws SQLException {
        String url = prop.getProperty(DatabaseProperties.URL);
        String user = prop.getProperty(DatabaseProperties.USER);
        String password = prop.getProperty(DatabaseProperties.PASSWORD);
        Connection connection = DriverManager.getConnection(url, user, password);
        log.info("Connection successfully created for db url: " + url);
        return connection;
    }

}

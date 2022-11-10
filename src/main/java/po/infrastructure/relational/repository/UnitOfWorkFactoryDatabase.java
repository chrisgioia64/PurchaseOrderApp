package po.infrastructure.relational.repository;

import po.domain.repository.UnitOfWork;
import po.domain.repository.UnitOfWorkFactory;
import po.infrastructure.relational.config.DatabaseProperties;
import po.infrastructure.relational.config.DatabaseUtils;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;

public class UnitOfWorkFactoryDatabase implements UnitOfWorkFactory {

    private DatabaseProperties prop;

    public UnitOfWorkFactoryDatabase(DatabaseProperties prop) {
        this.prop = prop;
    }

    @Override
    public UnitOfWork createUnitOfWork() {
        Connection connection = null;
        try {
            connection = DatabaseUtils.createConnection(prop);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return new UnitOfWorkDatabase(connection);
    }
}

package po.infrastructure.test.dao;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import po.domain.dto.Part;
import po.infrastructure.relational.config.DatabaseUtils;
import po.infrastructure.relational.dao.PartDao;
import po.infrastructure.relational.dao.PartDaoImpl;
import po.infrastructure.test.PersistenceTestGroups;

import javax.sql.DataSource;
import java.sql.SQLException;

@Test(groups = {PersistenceTestGroups.DAO})
public class PartDaoConcurrencyTest {

    private PartDao partDao1, partDao2;
    private Part p1, p2, p3;
    private DataSource dataSource1, dataSource2;

    @BeforeClass
    public void setupClass() throws SQLException {
        dataSource1 = DatabaseUtils.createDataSource(DatabaseUtils.unitProperties);

        partDao1 = new PartDaoImpl(dataSource1);
        p1 = Part.builder().name("Keyboard").price(49.99).build();
        p2 = Part.builder().name("Toshiba Laptop").price(849.99).build();
        p3 = Part.builder().name("Earphones").price(30).build();

        dataSource2 = DatabaseUtils.createDataSource(DatabaseUtils.unitProperties);
        partDao2 = new PartDaoImpl(dataSource2);
    }

    @BeforeMethod
    public void setupMethod() {
        partDao1.clearEntries();
    }

    /**
     * Types of concurrency tests:
     * -- Interleaving of transactions
     * -- Testing locking behavior
     */

}

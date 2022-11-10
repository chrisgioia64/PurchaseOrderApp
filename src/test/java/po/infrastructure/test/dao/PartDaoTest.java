package po.infrastructure.test.dao;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import po.domain.dto.Part;
import po.infrastructure.relational.config.DatabaseUtils;
import po.infrastructure.relational.dao.PartDao;
import po.infrastructure.relational.dao.PartDaoImpl;
import po.infrastructure.test.PersistenceTestGroups;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.testng.Assert.*;

@Test(groups = {PersistenceTestGroups.DAO})
public class PartDaoTest {

    private PartDao partDao;
    private Part p1, p2, p3;

    @BeforeClass
    public void setupClass() throws SQLException {
        Connection connection = DatabaseUtils.createConnection(DatabaseUtils.unitProperties);

        partDao = new PartDaoImpl(connection);
        p1 = Part.builder().name("Keyboard").price(49.99).build();
        p2 = Part.builder().name("Toshiba Laptop").price(849.99).build();
        p3 = Part.builder().name("Earphones").price(30).build();
    }

    @BeforeMethod
    public void setupMethod() {
        partDao.clearEntries();
    }

    @Test
    public void test1() {
        assertEquals(0, partDao.getParts().size());
    }

    @Test
    public void test2() {
        int id = partDao.addPart(p1.getName(), p1.getPrice());
        assertEquals(1, id);
        Part p = partDao.getPart(id);
        assertEquals(p, p1);
        assertEquals(id, p.getId());

        p = partDao.getPart(2);
        assertNull(p);
    }

    @Test
    public void test3() {
        partDao.addPart(p1.getName(), p1.getPrice());
        partDao.addPart(p2.getName(), p2.getPrice());

        List<Part> parts = partDao.getParts();
        assertEquals(2, parts.size());
        assertEquals(parts.get(0), p1);
        assertEquals(parts.get(1), p2);
    }

    @Test
    public void test4() {
        partDao.addPart(p1.getName(), p1.getPrice());

        Part p = partDao.getPart(1);
        assertEquals(p, p1);
        assertEquals(1, p.getId());

        p.setName("Keyboard 2");
        p.setPrice(60);
        assertTrue(partDao.updatePart(p));

        Part newPart = partDao.getPart(1);
        assertEquals(newPart.getName(), "Keyboard 2");
        assertEquals(newPart.getPrice(), 60);
    }

    @Test
    public void test5() {
        partDao.addPart(p1.getName(), p1.getPrice());

        Part p = partDao.getPart(1);
        p.setId(2);
        p.setName("Keyboard 2");

        assertFalse(partDao.updatePart(p));

        Part newPart = partDao.getPart(1);
        assertEquals(newPart.getName(), p1.getName());
    }

}

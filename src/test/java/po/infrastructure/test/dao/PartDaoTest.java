package po.infrastructure.test.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
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
import java.util.List;

import static org.testng.Assert.*;

@Test(groups = {PersistenceTestGroups.DAO})
public class PartDaoTest {

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

    /** Assert that no parts are in the database on startup. */
    @Test
    public void test1() {
        assertEquals(0, partDao1.getParts().size());
    }

    /** Add a single part, and assert its attributes
     */
    @Test
    public void test2() throws SQLException {
        int id = partDao1.addPart(p1.getName(), p1.getPrice());
        assertEquals(1, id);
        Part p = partDao1.getPart(id);
        assertEquals(p, p1);
        assertEquals(id, p.getId());
        dataSource1.getConnection().commit();

        try {
            p = partDao1.getPart(2);
            fail("should have thrown an exception");
        } catch (DataAccessException ex) {

        }
        dataSource2.getConnection().commit();
    }

    /**
     * Add a single part, and access the part by another datasource
     */
    @Test
    public void test2b() throws SQLException {
        int id = partDao1.addPart(p1.getName(), p1.getPrice());
        assertEquals(1, id);
        Part p = partDao1.getPart(id);
        assertEquals(p, p1);
        assertEquals(id, p.getId());
        dataSource1.getConnection().commit();

        p = partDao1.getPart(id);
        assertEquals(p, p1);
        assertEquals(id, p.getId());
        dataSource1.getConnection().commit();

        p = partDao2.getPart(id);
        assertEquals(p, p1);
        dataSource2.getConnection().commit();
    }

    /**
     * Add two parts, and verify both of them were added
     */
    @Test
    public void test3() throws SQLException {
        partDao1.addPart(p1.getName(), p1.getPrice());
        partDao1.addPart(p2.getName(), p2.getPrice());
        dataSource1.getConnection().commit();

        List<Part> parts = partDao2.getParts();
        assertEquals(2, parts.size());
        assertEquals(parts.get(0), p1);
        assertEquals(parts.get(1), p2);
        dataSource2.getConnection().commit();
    }

    /**
     * Verify add and update
     */
    @Test
    public void test4() throws SQLException {
        partDao1.addPart(p1.getName(), p1.getPrice());
        dataSource1.getConnection().commit();

        Part p = partDao2.getPart(1);
        assertEquals(p, p1);
        assertEquals(1, p.getId());

        p.setName("Keyboard 2");
        p.setPrice(60);
        assertTrue(partDao2.updatePart(p));
        dataSource2.getConnection().commit();

        Part newPart = partDao1.getPart(1);
        assertEquals(newPart.getName(), "Keyboard 2");
        assertEquals(newPart.getPrice(), 60);
        dataSource1.getConnection().commit();
    }

    /**
     * Trying to update a row on an id that doesn't exist
     * @throws SQLException
     */
    @Test
    public void test5() throws SQLException {
        partDao1.addPart(p1.getName(), p1.getPrice());
        dataSource1.getConnection().commit();

        Part p = partDao2.getPart(1);
        p.setId(2);
        p.setName("Keyboard 2");
        assertFalse(partDao2.updatePart(p));
        dataSource2.getConnection().commit();

        Part newPart = partDao1.getPart(1);
        assertEquals(newPart.getName(), p1.getName());
        dataSource1.getConnection().commit();
    }

    @Test
    public void test6() throws SQLException {
        partDao1.addPart(p1.getName(), p1.getPrice());
        dataSource1.getConnection().commit();

        try {
            Part nullPart = partDao1.getPart(5);
            fail("should have thrown exception");
        } catch (EmptyResultDataAccessException ex) {

        }
    }

}

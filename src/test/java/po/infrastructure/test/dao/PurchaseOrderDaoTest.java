package po.infrastructure.test.dao;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import po.domain.dto.Part;
import po.domain.dto.PurchaseOrder;
import po.domain.dto.PurchaseOrderLineItem;
import po.infrastructure.relational.config.DatabaseUtils;
import po.infrastructure.relational.dao.PartDao;
import po.infrastructure.relational.dao.PartDaoImpl;
import po.infrastructure.relational.dao.PurchaseOrderDao;
import po.infrastructure.relational.dao.PurchaseOrderDaoImpl;
import po.infrastructure.test.PersistenceTestGroups;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertNull;

@Test(groups = {PersistenceTestGroups.DAO})
public class PurchaseOrderDaoTest {

    private PurchaseOrderDao poDao1, poDao2;
    private PurchaseOrder p1, p2, p3;
    private DataSource dataSource1, dataSource2;

    @BeforeMethod
    public void setup() {
        System.out.println("setup");
    }

    @BeforeClass
    public void setupClass() throws SQLException {
        dataSource1 = DatabaseUtils.createDataSource(DatabaseUtils.unitProperties);

        poDao1 = new PurchaseOrderDaoImpl(dataSource1);
        p1 = PurchaseOrder.builder().name("PO1").approvedLimit(5000).build();
        p2 = PurchaseOrder.builder().name("PO2").approvedLimit(10000).build();

        dataSource2 = DatabaseUtils.createDataSource(DatabaseUtils.unitProperties);
        poDao2 = new PurchaseOrderDaoImpl(dataSource2);
    }

    @BeforeMethod
    public void setupBeforeMethod() throws SQLException {
        poDao1.clearEntries();
        dataSource1.getConnection().commit();
    }

    @Test
    public void test1() {
        assertEquals(0, poDao1.getAllPurchaseOrders().size());
    }

    /**
     * Add a purchase order, and assert the attributes
     */
    @Test
    public void test2() throws SQLException {
        int x = poDao1.addPurchaseOrder(p1);
        assertEquals(1, x);
        dataSource1.getConnection().commit();

        PurchaseOrder retrievedOrder = poDao1.getPurchaseOrder(x);
        dataSource1.getConnection().commit();
        dataSource1.getConnection().commit();
        assertEquals(retrievedOrder.getName(), p1.getName());
        assertEquals(retrievedOrder.getApprovedLimit(), p1.getApprovedLimit());
        assertEquals(retrievedOrder.getId(), 1);
        assertNull(retrievedOrder.getLineItems());
    }

    /**
     * Test auto-generated keys
     * @throws SQLException
     */
    @Test
    public void test3() throws SQLException {
        int x = poDao1.addPurchaseOrder(p1);
        dataSource1.getConnection().commit();
        assertEquals(1, x);

        int y = poDao1.addPurchaseOrder(p2);
        dataSource1.getConnection().commit();
        assertEquals(2, y);
    }

    /**
     * Test Get all purchase orders
     */
    @Test
    public void test4() throws SQLException {
        int x = poDao1.addPurchaseOrder(p1);
        assertEquals(1, x);
        dataSource1.getConnection().commit();

        List<PurchaseOrder> purchaseOrders = poDao1.getAllPurchaseOrders();
        dataSource1.getConnection().commit();
        assertEquals(1, purchaseOrders.size());
        PurchaseOrder retrievedOrder = purchaseOrders.get(0);
        dataSource1.getConnection().commit();
        assertEquals(retrievedOrder.getName(), p1.getName());
        assertEquals(retrievedOrder.getApprovedLimit(), p1.getApprovedLimit());
        assertEquals(retrievedOrder.getId(), 1);
        assertNull(retrievedOrder.getLineItems());
    }

    /**
     * Update and verify
     */
    @Test
    public void test5() throws SQLException {
        int x = poDao1.addPurchaseOrder(p1);
        assertEquals(1, x);
        dataSource1.getConnection().commit();

        PurchaseOrder retrievedOrder = poDao1.getPurchaseOrder(x);
        dataSource1.getConnection().commit();

        retrievedOrder.setName("new name");
        retrievedOrder.setApprovedLimit(15000);
        Part part = Part.builder().price(50).name("Paper").build();
        PurchaseOrderLineItem lineItem = PurchaseOrderLineItem.builder().part(part)
                        .quantity(5).build();
        List<PurchaseOrderLineItem> lineItems = new LinkedList<>();
        lineItems.add(lineItem);
        retrievedOrder.setLineItems(lineItems);
        poDao1.updatePurchaseOrder(retrievedOrder);
        dataSource1.getConnection().commit();

        PurchaseOrder retrievedOrder2 = poDao1.getPurchaseOrder(x);
        dataSource1.getConnection().commit();

        assertEquals(retrievedOrder2.getName(), retrievedOrder.getName());
        assertEquals(retrievedOrder2.getApprovedLimit(), retrievedOrder.getApprovedLimit());
        assertNull(retrievedOrder2.getLineItems());
    }

}

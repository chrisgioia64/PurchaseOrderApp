package po.infrastructure.test.dao;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import po.infrastructure.test.PersistenceTestGroups;

import static org.testng.Assert.assertEquals;

@Test(groups = {PersistenceTestGroups.DAO})
public class PurchaseOrderDaoTest {

    @BeforeMethod
    public void setup() {
        System.out.println("setup");
    }

}

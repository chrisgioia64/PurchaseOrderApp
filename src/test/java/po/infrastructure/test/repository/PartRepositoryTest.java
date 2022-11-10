package po.infrastructure.test.repository;

import org.testng.annotations.Test;
import po.infrastructure.test.PersistenceTestGroups;

import static org.testng.Assert.assertEquals;

@Test(groups = {PersistenceTestGroups.REPOSTIORY},
        dependsOnGroups = {PersistenceTestGroups.DAO})
public class PartRepositoryTest {

    public void foo() {
        assertEquals(1, 1);
    }

}

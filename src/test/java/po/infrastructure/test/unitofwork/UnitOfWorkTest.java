package po.infrastructure.test.unitofwork;

import org.testng.annotations.Test;
import po.infrastructure.test.PersistenceTestGroups;

@Test(groups = {PersistenceTestGroups.UNIT_OF_WORK},
    dependsOnGroups = {PersistenceTestGroups.REPOSTIORY})
public class UnitOfWorkTest {

}

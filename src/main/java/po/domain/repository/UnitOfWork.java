package po.domain.repository;

import po.domain.repository.PartRepository;
import po.domain.repository.PurchaseOrderRepository;

public interface UnitOfWork {

    PartRepository getPartRepository();

    PurchaseOrderRepository getPurchaseOrderRepository();

    boolean commit();

}

package po.infrastructure.relational.repository;

import po.domain.repository.PartRepository;
import po.domain.repository.PurchaseOrderRepository;
import po.domain.repository.UnitOfWork;

import java.sql.Connection;

public class UnitOfWorkDatabase implements UnitOfWork {

    private Connection connection;

    public UnitOfWorkDatabase(Connection connection) {
        this.connection = connection;
    }

    @Override
    public PartRepository getPartRepository() {
        return null;
    }

    @Override
    public PurchaseOrderRepository getPurchaseOrderRepository() {
        return null;
    }

    @Override
    public boolean commit() {
        return false;
    }
}

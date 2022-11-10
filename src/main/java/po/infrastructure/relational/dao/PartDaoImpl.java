package po.infrastructure.relational.dao;

import po.domain.dto.Part;

import java.sql.Connection;
import java.util.List;

public class PartDaoImpl implements PartDao {

    private Connection connection;

    public PartDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int addPart(String name, double price) {
        return -1;
    }

    @Override
    public Part getPart(int id) {
        return null;
    }

    @Override
    public boolean updatePart(Part part) {
        return false;
    }

    @Override
    public List<Part> getParts() {
        return null;
    }

    @Override
    public void clearEntries() {

    }
}

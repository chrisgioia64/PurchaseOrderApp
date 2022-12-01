package po.infrastructure.relational.repository;

import po.domain.dto.Part;
import po.domain.repository.PartRepository;
import po.infrastructure.relational.dao.PartDao;

import java.util.List;

public class PartRepositoryImpl implements PartRepository {

    private PartDao partDao;

    public PartRepositoryImpl(PartDao dao) {
        this.partDao = dao;
    }

    @Override
    public int addPart(String name, double price) {
        return partDao.addPart(name, price);
    }

    @Override
    public Part getPart(int id) {
        return partDao.getPart(id);
    }

    @Override
    public boolean updatePart(Part part) {
        return partDao.updatePart(part);
    }

    @Override
    public List<Part> getParts() {
        return partDao.getParts();
    }

    @Override
    public void clearEntries() {
        partDao.clearEntries();
    }
}

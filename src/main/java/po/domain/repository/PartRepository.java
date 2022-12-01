package po.domain.repository;

import po.domain.dto.Part;

import java.util.List;

public interface PartRepository {

    /**
     * Returns the id of the part created in the database.
     * Returns -1 if could not add to the database
     */
    int addPart(String name, double price);

    /**
     * Return the part by the database id
     */
    Part getPart(int id);

    /**
     * Updates the part to the database.
     * Return true if successful.
     */
    boolean updatePart(Part part);

    /**
     * Returns all the parts from the database.
     */
    List<Part> getParts();

    void clearEntries();

}

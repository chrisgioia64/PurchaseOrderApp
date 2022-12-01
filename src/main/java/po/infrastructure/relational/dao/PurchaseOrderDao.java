package po.infrastructure.relational.dao;

import po.domain.dto.PurchaseOrder;

import java.util.List;

public interface PurchaseOrderDao {

    /**
     * Creates a purchase order row in the PurchaseOrder table.
     * Does not create any line order items in the table
     * Returns the id of the row created in the database
     */
    public int addPurchaseOrder(PurchaseOrder order);

    /**
     * Returns all purchase orders, without any of the associated line order items
     */
    public List<PurchaseOrder> getAllPurchaseOrders();

    /**
     * Returns a purchase order object without any of the associated line order items
     */
    public PurchaseOrder getPurchaseOrder(int id);

    /**
     * Update the purchase order object without the associated line order items
     *
     * Returns true if successful
     */
    public boolean updatePurchaseOrder(PurchaseOrder order);

    /**
     * Clear all purchase order table entries
     */
    public void clearEntries();

}

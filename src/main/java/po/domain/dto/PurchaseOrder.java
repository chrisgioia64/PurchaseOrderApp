package po.domain.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class PurchaseOrder {

    @EqualsAndHashCode.Exclude
    private long id;

    /** When you create the Purchase Order, it must have a name, so it must
     * always have a non-null value. */
    @NonNull
    private String name;

    private List<PurchaseOrderLineItem> lineItems;

    @NonNull
    private int approvedLimit;

    /**
     * This is a transient field (does not need to be persisted)
     */
    private double totalPrice;

    public boolean isUnderApprovedLimit() {
        double totalPrice = 0;
        for (PurchaseOrderLineItem lineItem : lineItems) {
            totalPrice += lineItem.getPart().getPrice();
        }
        return totalPrice <= approvedLimit;
    }

}

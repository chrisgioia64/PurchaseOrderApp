package po.domain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class PurchaseOrderLineItem {

    @EqualsAndHashCode.Exclude
    private long id;

    @NonNull
    private Integer quantity;

    @NonNull
    private Part part;

}

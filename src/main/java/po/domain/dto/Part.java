package po.domain.dto;

import lombok.*;

@Data
@Builder
public class Part {

    private long id;

    @NonNull
    private String name;

    @NonNull
    private double price;

}

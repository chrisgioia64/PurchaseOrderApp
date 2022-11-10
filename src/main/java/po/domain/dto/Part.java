package po.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Part {

    private long id;

    @NonNull
    private String name;

    @NonNull
    private double price;

}

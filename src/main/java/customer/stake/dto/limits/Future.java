package customer.stake.dto.limits;

import customer.stake.enums.Interval;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class Future {

    private Double value;
    private Interval interval;
    private String from;

    public Future(Double value, Interval interval, String from) {
        this.value = value;
        this.interval = interval;
        this.from = from;
    }
}

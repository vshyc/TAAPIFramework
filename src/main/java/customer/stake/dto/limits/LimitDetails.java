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
public class LimitDetails {

    private Double value;
    private Interval interval;

    public LimitDetails(Double value, Interval interval) {
        this.value = value;
        this.interval = interval;
    }
}
